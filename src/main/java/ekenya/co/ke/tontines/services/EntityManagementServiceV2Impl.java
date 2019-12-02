package ekenya.co.ke.tontines.services;

import ekenya.co.ke.tontines.dao.entitites.*;
import ekenya.co.ke.tontines.dao.entitites.accounting.*;
import ekenya.co.ke.tontines.dao.repositories.jpql.GetExternalGroupAccounts;
import ekenya.co.ke.tontines.dao.repositories.jpql.GetGroupStatements;
import ekenya.co.ke.tontines.dao.repositories.jpql.ViewMemberGroups;
import ekenya.co.ke.tontines.dao.wrappers.Response;
import ekenya.co.ke.tontines.dao.wrappers.StatementGetWrapper;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.ExternalAccountDetailsWrapper;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.RecordBalanceWrapper;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.AcceptInviteWrapper;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.AddRoleWrapper;
import ekenya.co.ke.tontines.services.accounting.AccountingRequirementsService;
import ekenya.co.ke.tontines.services.accounting.AccountingService;
import ekenya.co.ke.tontines.services.portal.DataRequirementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.*;
import java.lang.reflect.Member;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class EntityManagementServiceV2Impl implements EntityManagementServiceV2 {
    private final static Logger logger = Logger.getLogger(EntityManagementServiceImpl.class.getName());

    @Autowired private AccountingService accountingService;
    @Autowired private EntityServicesRequirementsV1 entityServicesRequirementsV1;
    @Autowired private EntityServicesRequirementsV2 entityServicesRequirementsV2;
    @Autowired private DataRequirementsService dataRequirementsService;

    @Autowired
    @Lazy
    private AccountingRequirementsService accountingRequirementsService;

    @Value("${app-configs.documents-storage}")
    private String documentStorage;
    @Override
    public UniversalResponse RECORD_EXTERNAL_ACCOUNT(RecordBalanceWrapper recordBalanceWrapper) {

        List<MemberGroups> memberGroupsList = entityServicesRequirementsV2.findMemberGroups(recordBalanceWrapper.getGroupId());

        if (memberGroupsList.size() > 0){
            RecordBalance recordBalance = new RecordBalance();
            recordBalance.setAccountName(recordBalanceWrapper.getAccountName());
            recordBalance.setAccountNumber(recordBalanceWrapper.getAccountNumber());
            MemberGroups m = memberGroupsList.get(0);
            recordBalance.setMemberGroup(m);

            ExternalAccountTypes accountTypes = new ExternalAccountTypes();
            accountTypes.setId(recordBalanceWrapper.getExternalAccountTypeId());
            recordBalance.setAccountTypes(accountTypes);

            ExternalAccountDetailsWrapper createdRecord = accountingService.recordExternalAccount(recordBalance,
                    recordBalanceWrapper.getAmount());

            return new UniversalResponse(new Response(200,"external account" +
                    " created successfully"),createdRecord);
        }else{
            return new UniversalResponse(new Response(404,"member group not found"));
        }

    }

    @Override
    public UniversalResponse GET_CONTRIBUTION_SOURCES() {
        return new UniversalResponse(new Response(200,"contribution sources retrieved " +
                "successfully"),dataRequirementsService.getCotnributionSource());
    }

    @Override
    public UniversalResponse MAKE_CONTRIBUTION(ContributionsLog contributionsLog) {

        Members members = contributionsLog.getMember();
        List<AccountNumber> accountNumberList = accountingService.findAccountNumber(members.getId(),
                Members.class.getSimpleName());

        if (accountNumberList.size() > 0){

            List<Contributions> contributionsList = entityServicesRequirementsV2.getContributionById(
                    contributionsLog.getContribution().getId()
            );

            if (contributionsList.size() > 0){

                Contributions contributions = contributionsList.get(0);

                long memberGroupId = contributions.getMemberGroup().getId();

                List<AccountNumber> groupAccount =accountingService.findAccountNumber(
                        memberGroupId,
                        MemberGroups.class.getSimpleName()
                );

                if (groupAccount.size() > 0 ){
                    AccountNumber accountNumber = accountNumberList.get(0);
                    AccountBalances accountBalances = accountingService.geAccountBalance(accountNumber);

                    if (Integer.parseInt(accountBalances.getActualBalance()) >=
                            Integer.parseInt(contributionsLog.getAmount())){

                        ContributionsLog createdLog = entityServicesRequirementsV2.
                                addContributionLog(contributionsLog);

                        TransactionsLog transactionsLog = accountingService.createTransactionLog(
                                createdLog.getId(),
                                ContributionsLog.class.getSimpleName(),
                                "Member contribution"
                        );

                        accountingService.initiateLedgerTransactionLog(
                                groupAccount.get(0),
                                accountNumber,
                                transactionsLog,
                                contributionsLog.getAmount()
                        );


                        return new UniversalResponse(new Response(200,"transaction has been processed " +
                                "successfully"));

                    }else{
                        return new UniversalResponse(new Response(405,"Insuffient account balance to " +
                                "complete this transaction"));
                    }
                }else{
                    return new UniversalResponse(new Response(407,"Group account not found"));
                }

            }else{
                return new UniversalResponse(new Response(406,"Contribution not found"));
            }




        }else{
            return new UniversalResponse(new Response(404,"member not found"));
        }

    }

    @Override
    public UniversalResponse GET_CONTRIBUTION_STATEMENTS(StatementGetWrapper wrapper) {

        Pageable pageable = PageRequest.of(wrapper.getPage(),wrapper.getSize());

        List<Contributions> contributions = entityServicesRequirementsV2.getContributionById(wrapper.getId());

        if (contributions.size()>0){
            Page<ContributionsLog> contributionsLogPage = entityServicesRequirementsV2.getContributionsLogs(
                   contributions.get(0) ,pageable
            );
            return new UniversalResponse(new Response(200,"contributons statements retrived successfully"),
                    contributionsLogPage);
        }else{
            return new UniversalResponse(new Response(404,"contributon id not found"));
        }

    }

    @Override
    public UniversalResponse GET_GROUP_STATEMENTS(StatementGetWrapper wrapper) {

        Pageable pageable = PageRequest.of(wrapper.getPage(),wrapper.getSize());
        long groupId = wrapper.getId();

        Page<GetGroupStatements> statementsPage = entityServicesRequirementsV2.getGroupContributions(groupId,
                pageable);

        String message = statementsPage.getTotalElements()+" elements found";

        return new UniversalResponse(new Response(200,message),statementsPage);
    }

    @Override
    public UniversalResponse GET_GROUP_FOR_MEMBER(StatementGetWrapper wrapper) {

        Pageable pageable = PageRequest.of(wrapper.getPage(),wrapper.getSize());
        long id = wrapper.getId();
        Page<ViewMemberGroups> viewMemberGroups = entityServicesRequirementsV2.getMemberGroupsForMember(id,
                pageable);

        String message = viewMemberGroups.getTotalElements()+" groups found";
        return new UniversalResponse(new Response(200,message),viewMemberGroups);
    }

    @Override
    public UniversalResponse GET_GROUP_FOR_MEMBER_INVITES(StatementGetWrapper wrapper) {
        Pageable pageable = PageRequest.of(wrapper.getPage(),wrapper.getSize());
        long id = wrapper.getId();
        Page<ViewMemberGroups> viewMemberGroups = entityServicesRequirementsV2.getMemberGroupsForMemberInvites(id,
                pageable);

        String message = viewMemberGroups.getTotalElements()+" groups invites found";
        return new UniversalResponse(new Response(200,message),viewMemberGroups);
    }


    @Override
    public UniversalResponse ACCEPT_GROUP_INVITE(List<AcceptInviteWrapper> list) {


        for (AcceptInviteWrapper inviteWrapper : list){
            MemberGroups groups = new MemberGroups();
            groups.setId(inviteWrapper.getGroupId());
            Members members = new Members();
            members.setId(inviteWrapper.getMemberId());
            List<MemberAndGroupLink> memberAndGroupLinkList = entityServicesRequirementsV2.findMemberGroupAndMemberLink(
                    groups,members
            );

            if (memberAndGroupLinkList.size() > 0){

                MemberAndGroupLink link = memberAndGroupLinkList.get(0);
                link.setHasAcceptedInvite(true);

                MemberAndGroupLink l = entityServicesRequirementsV2.addMemberGroupLink(link);

            }else{

            }

        }

        return new UniversalResponse(new Response(200,"group updated successfully"));


    }

    @Override
    public UniversalResponse DECLINE_GROUP_INVITE(AcceptInviteWrapper acceptInviteWrapper) {
        MemberGroups groups = new MemberGroups();
        groups.setId(acceptInviteWrapper.getGroupId());
        Members members = new Members();
        members.setId(acceptInviteWrapper.getMemberId());
        List<MemberAndGroupLink> memberAndGroupLinkList = entityServicesRequirementsV2.findMemberGroupAndMemberLink(
                groups,members
        );

        MemberAndGroupLink link = memberAndGroupLinkList.get(0);
        link.setDeclinedInvite(true);
        entityServicesRequirementsV2.addMemberGroupLink(link);

        return new UniversalResponse(new Response(200,"group invite declined"));
    }

    @Override
    public UniversalResponse ADD_MEMBER_ROLE(AddRoleWrapper addRoleWrapper) {
        MemberGroups groups = new MemberGroups();
        groups.setId(addRoleWrapper.getGroupId());
        Members members = new Members();
        members.setId(addRoleWrapper.getMemberId());

        MemberRoles roles = new MemberRoles();
        roles.setId(addRoleWrapper.getRoleId());
        List<MemberAndGroupLink> memberAndGroupLinkList = entityServicesRequirementsV2.findMemberGroupAndMemberLink(
                groups,members
        );
        MemberAndGroupLink link = memberAndGroupLinkList.get(0);
        link.setMemberRole(roles);

        entityServicesRequirementsV2.addMemberGroupLink(link);
        return new UniversalResponse(new Response(200,"role updated successfully"));
    }


    @Override
    public UniversalResponse UPLOAD_GROUP_DOCUMENTS(MultipartFile[] files, long id) {

        logger.info("total files---"+files.length);

        List<DocumentsLibrary> fileNames = new ArrayList<>();
        for(MultipartFile file : files){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            try {
                logger.info(documentStorage+fileName);
                FileWriter uploadDir = new FileWriter(documentStorage+fileName);
                uploadDir.flush();
                uploadDir.close();
            } catch (IOException e) {
                e.printStackTrace();
            }



            logger.info("file name---"+fileName);


            Path p = Paths.get(documentStorage).toAbsolutePath().normalize();
            try {
                Path path = p.resolve(fileName).normalize();
                InputStream s = file.getInputStream();
                Files.copy(s,path,StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return new UniversalResponse(new Response(200,"file upload"),fileNames);
    }

    @Override
    public UniversalResponse UPLOAD_GROUP_DOCUMENTS(List<Part> parts, long id) {
        List<DocumentsLibrary> fileNames = new ArrayList<>();
        for (Part p : parts){

            try {

                String[] files = p.getSubmittedFileName().split(".");
        //        String extension = files[1];
                // TODO :: EXCEPTION HANDLING FOR FILES WITHOUT REGEX
                FileWriter fileWriter = new FileWriter(documentStorage+p.getSubmittedFileName());
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Path pa = Paths.get(documentStorage).toAbsolutePath().normalize();
            try {
                Path path = pa.resolve(p.getSubmittedFileName()).normalize();
                InputStream s = p.getInputStream();
                Files.copy(s,path,StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }

            MemberGroups groups = new MemberGroups();
            groups.setId(id);

            DocumentsLibrary documentsLibrary = new DocumentsLibrary();
            documentsLibrary.setDocumentFolder(documentStorage);
            documentsLibrary.setDocumentName(p.getSubmittedFileName());
            documentsLibrary.setMemberGroups(groups);

            String  downloadId = UUID.randomUUID().toString();
            String url = "/documents/download/"+downloadId;
            documentsLibrary.setDownloadUrl(url);

            DocumentsLibrary uploaded = entityServicesRequirementsV2.createDocumentDirectory(documentsLibrary);
            fileNames.add(uploaded);


        }
        return new UniversalResponse(new Response(200,"file upload"),fileNames);
    }

    @Override
    public UniversalResponse GET_EXTERNAL_ACCOUNT_TYPES() {
        List<ExternalAccountTypes> accountTypes = entityServicesRequirementsV2.getExternalAccountTypes();
        return new UniversalResponse(new Response(200, "external account types "),
                accountTypes);
    }

    @Override
    public UniversalResponse GET_GROUP_EXTERNAL_ACCOUNTS(StatementGetWrapper wrapper) {

        long memberGroupid = wrapper.getId();
        long accountType = wrapper.getAccountTypeId();
        Pageable pageable = PageRequest.of(wrapper.getPage(),wrapper.getSize());

        MemberGroups m = new MemberGroups();
        m.setId(memberGroupid);
        ExternalAccountTypes e = new ExternalAccountTypes();
        e.setId(accountType);
        Page<GetExternalGroupAccounts> getExternalGroupAccountsPage =
                accountingRequirementsService.getExternalGroupAccounts(m,e,pageable);

        return new UniversalResponse(new Response(),getExternalGroupAccountsPage) ;
    }
}

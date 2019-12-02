package ekenya.co.ke.tontines.services;

import ekenya.co.ke.tontines.dao.entitites.Contributions;
import ekenya.co.ke.tontines.dao.entitites.ContributionsLog;
import ekenya.co.ke.tontines.dao.wrappers.StatementGetWrapper;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.RecordBalanceWrapper;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.AcceptInviteWrapper;
import ekenya.co.ke.tontines.dao.wrappers.membergroups.AddRoleWrapper;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.util.List;

/**
 * contains record balance
 */
public interface EntityManagementServiceV2 {


    UniversalResponse RECORD_EXTERNAL_ACCOUNT(RecordBalanceWrapper recordBalanceWrapper);
    UniversalResponse GET_CONTRIBUTION_SOURCES();
    UniversalResponse MAKE_CONTRIBUTION(ContributionsLog contributionsLog);
    UniversalResponse GET_CONTRIBUTION_STATEMENTS(StatementGetWrapper wrapper);
    UniversalResponse GET_GROUP_STATEMENTS(StatementGetWrapper wrapper);
    UniversalResponse GET_GROUP_FOR_MEMBER(StatementGetWrapper wrapper);
    UniversalResponse GET_GROUP_FOR_MEMBER_INVITES(StatementGetWrapper wrapper);

    UniversalResponse ACCEPT_GROUP_INVITE(List<AcceptInviteWrapper> inviteWrapper);
    UniversalResponse DECLINE_GROUP_INVITE(AcceptInviteWrapper acceptInviteWrapper);

    UniversalResponse ADD_MEMBER_ROLE(AddRoleWrapper addRoleWrapper);

    UniversalResponse UPLOAD_GROUP_DOCUMENTS(MultipartFile[] files, long id);
    UniversalResponse UPLOAD_GROUP_DOCUMENTS(List<Part> parts, long id);

    UniversalResponse GET_EXTERNAL_ACCOUNT_TYPES();

    UniversalResponse GET_GROUP_EXTERNAL_ACCOUNTS(StatementGetWrapper wrapper);
}

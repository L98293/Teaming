package teaming.Teaming.Invite;

import teaming.Teaming.Domain.Project;

import java.util.List;

public interface InviteService {
    void sendInvite(Long managerId, Long normalId, Long projectId);

    void acceptInvite(Long inviteId);
}

package deo.coherence.helpers;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.Cluster;
import com.tangosol.net.Member;

import java.util.Collections;
import java.util.Map;

public class MemberUtils {

    public static void setLocalMemberRole(String role) {
        SystemPropertiesLoader.setSystemProperty("tangosol.coherence.role", role);
    }

    public static Map<String, Object> getLocalMemberInfo() {
        Cluster cluster = CacheFactory.getCluster();
        Member member = cluster.getLocalMember();
        return Collections.singletonMap("Role", member.getRoleName());
    }
}

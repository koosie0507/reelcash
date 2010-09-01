package com.google.code.reelcash;

import com.google.code.reelcash.data.ReelcashDataSource;
import com.google.code.reelcash.data.sql.QueryMediator;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Insert some documentation for the class <b>PermissionTable</b> (what's its purpose,
 * why this implementation, that sort of thing).
 *
 * @author andrei.olar
 */
public final class PermissionTable {

    private static final HashMap<Permission, String> permissionMap = new HashMap<Permission, String>(2);
    private static final String TEXT = "_permission_desc";

    static {
        permissionMap.put(Permission.EMIT, GlobalResources.getString(Permission.EMIT.getName().concat(TEXT)));
        permissionMap.put(Permission.RECEIVE, GlobalResources.getString(Permission.RECEIVE.getName().concat(TEXT)));
    }

    /**
     * Initializes the permissions table.
     */
    public static void init() {
        QueryMediator mediator = new QueryMediator(ReelcashDataSource.getInstance());
        try {
            mediator.execute("delete from permissions;");
            for (Permission p : permissionMap.keySet()) {
                mediator.execute("insert into permissions(name, description) values (?, ?);", p.getData(), permissionMap.get(p));
            }
        }
        catch (SQLException e) {
            throw new ReelcashException(e);
        }

    }
}

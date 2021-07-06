package vboled.netcracker.musicstreamer.model;

public enum Permission {
    USER_CHANGE("user:perm"),
    OWNER_PERMISSION("owner:perm"),
    ADMIN_PERMISSION("admin:perm");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

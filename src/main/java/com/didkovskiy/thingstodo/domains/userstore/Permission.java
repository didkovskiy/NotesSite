package com.didkovskiy.thingstodo.domains.userstore;

public enum Permission {
    READ("msg:read"), DELETE("msg:delete");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}

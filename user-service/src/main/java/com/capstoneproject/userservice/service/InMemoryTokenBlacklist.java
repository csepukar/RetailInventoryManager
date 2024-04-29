package com.capstoneproject.userservice.service;

import java.util.HashSet;
import java.util.Set;

public class InMemoryTokenBlacklist implements TokenBlacklist{
    private final Set<String> blacklist = new HashSet<>();

    @Override
    public void addToBlacklist(String token) {
        blacklist.add(token);
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}
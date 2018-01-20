package com.example.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PostboxImpl implements Postbox {
    private final ConcurrentHashMap<String, List<Email>> box = new ConcurrentHashMap<>();

    @Override
    public void putIn(Email email) {

        String recipients = format(email.getRecipients());
        if (recipients == null || recipients.isEmpty()) {
            return;
        }
        String[] recipientArr = recipients.split(",");
        for (String recip : recipientArr) {
            box.putIfAbsent(recip, Collections.emptyList());
            box.compute(recip, (k, v) -> {
                ArrayList<Email> emails = new ArrayList<>();
                emails.add(email);
                emails.addAll(v);
                return Collections.unmodifiableList(emails);
            });
        }
    }

    @Override
    public boolean hasEmail(String recipient) {
        return  box.getOrDefault(recipient,Collections.emptyList()).size()>0;
    }

    @Override
    public int size() {
        return box.values().stream().mapToInt(v -> v.size()).sum();
    }

    @Override
    public List<Email> find(String recipient) {
        return box.get(recipient);
    }

    private String format(String recipients) {
        if (recipients == null) {
            return null;
        }
        return recipients.replaceAll("^[\\s,]+|[\\s,]+$", "")
                .replaceAll("[,\\s]+,\\s*", ",");
    }
}

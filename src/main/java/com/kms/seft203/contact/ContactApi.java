package com.kms.seft203.contact;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
public class ContactApi {
    private static final Map<String, Contact> DATA = new HashMap<>();

    @GetMapping
    public List<Contact> getAll() {
        return new ArrayList<>(DATA.values());
    }

    @GetMapping("/{id}")
    public Contact getOne(@PathVariable String id) {
        return DATA.get(id);
    }

    @PostMapping
    public Contact create(@RequestBody SaveContactRequest request) {
        DATA.put(request.getId(), request);
        return request;
    }

    @PutMapping("/{id}")
    public Contact update(@PathVariable String id, @RequestBody SaveContactRequest request) {
        DATA.put(id, request);
        return request;
    }

    @DeleteMapping("/{id}")
    public Contact delete(@PathVariable String id) {
        return DATA.remove(id);
    }
}

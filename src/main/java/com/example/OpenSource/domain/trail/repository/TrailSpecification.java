package com.example.OpenSource.domain.trail.repository;

import com.example.OpenSource.domain.trail.domain.Trail;
import org.springframework.data.jpa.domain.Specification;

public class TrailSpecification {

    public static Specification<Trail> addressStartsWith(String address) { //
        return (root, query, builder) -> builder.like(root.get("lnmAddr"), address + "%");
    }

    public static Specification<Trail> coursLevelEquals(String coursLevelNm) {
        return (root, query, builder) -> builder.equal(root.get("coursLevelNm"), coursLevelNm);
    }

    public static Specification<Trail> coursLtCnEquals(String coursLtCn) {
        return (root, query, builder) -> builder.equal(root.get("coursLtCn"), coursLtCn);
    }
}
package com.codestates.flyaway.domain.memberimage.repository;

import com.codestates.flyaway.domain.memberimage.entity.MemberImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberImageRepository extends JpaRepository<MemberImage, Long> {
}

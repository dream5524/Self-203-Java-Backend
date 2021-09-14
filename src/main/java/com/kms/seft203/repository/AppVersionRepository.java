package com.kms.seft203.repository;

import com.kms.seft203.entity.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {
}

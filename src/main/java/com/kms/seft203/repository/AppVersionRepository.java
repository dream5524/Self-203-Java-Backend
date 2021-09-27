package com.kms.seft203.repository;

import com.kms.seft203.entity.AppVersion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Component
public interface AppVersionRepository extends JpaRepository<AppVersion, Long> {
}

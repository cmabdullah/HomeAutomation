package com.abdullah.home.automation.repository;

import com.abdullah.home.automation.domain.model.Switch;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SwitchRepository extends CrudRepository<Switch, Long> {

    Optional<Switch> findBySwitchName(String switchName);
}

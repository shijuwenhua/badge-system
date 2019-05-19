package com.shijuwenhua.signin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shijuwenhua.signin.model.Activity;
import com.shijuwenhua.signin.model.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

	Badge findById(long id);

	void deleteById(Long id);

	@Query("select t from Badge t where t.id != ?1 and t.upgradeBadgeId != ?1")
	List<Badge> getEditBadgesList(long id);

	@Query("select t from Badge t where t.upgradeBadgeId = ?1")
	List<Badge> getSubBadgesList(long id);

//    @Query("select t from Badge t where t.id = (select b.upgradeBadgeId from Badge b where b.id = ?1)")
	@Query(value = "select b1.* from badge b1, badge b2 where b1.id = b2.upgrade_badge_id and b2.id = ?1", nativeQuery = true)
	Badge getUpgradeBadge(long id);
}
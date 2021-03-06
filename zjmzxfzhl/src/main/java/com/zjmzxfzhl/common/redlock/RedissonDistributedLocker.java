package com.zjmzxfzhl.common.redlock;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Component
public class RedissonDistributedLocker implements DistributedLocker {

	public final static String LOCKER_PREFIX = "redlock:";

	@Autowired
	RedissonClient redissonClient;

	@Override
	public RLock getLock(String lockKey) {
		return redissonClient.getLock(getRlockKey(lockKey));
	}

	@Override
	public RLock lock(String lockKey) {
		RLock lock = getLock(lockKey);
		lock.lock();
		return lock;
	}

	@Override
	public RLock lock(String lockKey, long leaseTime) {
		RLock lock = getLock(lockKey);
		lock.lock(leaseTime, TimeUnit.SECONDS);
		return lock;
	}

	@Override
	public RLock lock(String lockKey, long leaseTime, TimeUnit unit) {
		RLock lock = getLock(lockKey);
		lock.lock(leaseTime, unit);
		return lock;
	}

	@Override
	public boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
		RLock lock = getLock(lockKey);
		try {
			return lock.tryLock(waitTime, leaseTime, unit);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean tryLock(RLock lock, long waitTime, long leaseTime, TimeUnit unit) {
		if (lock == null) {
			return false;
		}
		try {
			return lock.tryLock(waitTime, leaseTime, unit);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void unlock(String lockKey) {
		RLock lock = getLock(lockKey);
		if (lock.isLocked()) {
			lock.unlock();
		}
	}

	@Override
	public void unlock(RLock lock) {
		if (lock.isLocked()) {
			lock.unlock();
		}
	}

	public String getRlockKey(String lockKey) {
		return LOCKER_PREFIX + lockKey;
	}
}

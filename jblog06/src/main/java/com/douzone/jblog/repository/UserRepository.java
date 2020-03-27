package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.UserVo;

@Repository
public class UserRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public Boolean join(UserVo vo) {
		int count = sqlSession.insert("user.insert", vo);
		return count == 1;
	}

	public UserVo findByIdAndPassword(UserVo vo) {
		return sqlSession.selectOne("user.findByIdAndPassword", vo);
	}

	public UserVo findById(UserVo vo) {
		
		return sqlSession.selectOne("user.findById",vo);
	}

}

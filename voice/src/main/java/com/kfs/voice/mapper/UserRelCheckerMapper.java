package com.kfs.voice.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kfs.voice.entity.UserRelChecker;
import com.kfs.voice.vo.UserRelCheckerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRelCheckerMapper extends BaseMapper<UserRelChecker> {

    @Select("SELECT temp_rel.id AS id, u.id AS userId, u.cn_name AS username, temp_rel.checkerId, temp_rel.checkername " +
            "FROM user u " +
            "LEFT JOIN (" +
            "SELECT urc.id, urc.user_id AS userId, urc.checker_id AS checkerId, cu.cn_name AS checkername " +
            "FROM user_rel_checker urc JOIN user cu ON urc.checker_id = cu.id" +
            ") temp_rel ON u.id = temp_rel.userId " +
            "${ew.customSqlSegment}"
    )
    List<UserRelCheckerVo> getUserRelCheckerList(@Param(Constants.WRAPPER) QueryWrapper wrapper);

    @Select("SELECT " +
            "temp_rel.id AS id, u.id AS userId, u.cn_name AS username, temp_user_record.record_count AS finishNum, " +
            "(SELECT count(*) FROM sentence) AS totalNum, " +
            "temp_rel.checkerId, temp_rel.checkername " +
            "FROM user u " +
            "LEFT JOIN (" +
            "SELECT t_urc.id, t_urc.user_id AS userId, t_urc.checker_id AS checkerId, t_cu.cn_name AS checkername " +
            "FROM user_rel_checker t_urc JOIN user t_cu ON t_urc.checker_id = t_cu.id" +
            ") temp_rel ON u.id = temp_rel.userId " +
            "LEFT JOIN (" +
            "SELECT t_u.id, count(*) AS record_count " +
            "FROM user t_u JOIN record r ON t_u.id = r.user_id GROUP BY t_u.id" +
            ") temp_user_record ON u.id = temp_user_record.id " +
            "${ew.customSqlSegment}"
    )
    List<UserRelCheckerVo> getUserRelCheckerListByCheckerId(@Param(Constants.WRAPPER) QueryWrapper wrapper);
}

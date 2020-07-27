package life.majiang.community.community.mapper;


import life.majiang.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into user(accountId, name, token, gmt_create, gmt_modified) values (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(User user);
}

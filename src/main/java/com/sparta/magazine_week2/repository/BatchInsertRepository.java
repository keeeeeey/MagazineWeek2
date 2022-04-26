package com.sparta.magazine_week2.repository;

import com.sparta.magazine_week2.entity.PostImage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BatchInsertRepository {

    private final JdbcTemplate jdbcTemplate;

    private final int batchSize = 500;

    public void postImageSaveAll(List<PostImage> imgList) {
        List<PostImage> bowl = new ArrayList<>();
        for (int i = 0; i < imgList.size(); i++) {
            bowl.add(imgList.get(i));
            if ((i + 1) % batchSize == 0) {
                //배치사이즈가 되면 전체 인서트
                postImageBatchInsert(bowl);
            }
        }

        //배치 사이즈(500)를 못채우고 나오거나, 총데이터가 505개라 5개가 남으면 남은것 인서트
        if (!bowl.isEmpty()) {
            postImageBatchInsert(bowl);
        }
    }

    private void postImageBatchInsert(List<PostImage> bowl) {
        jdbcTemplate.batchUpdate("insert into post_image(`post_img`, `post_id`) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, bowl.get(i).getPostImg());
                        ps.setString(2, String.valueOf(bowl.get(i).getPost().getId()));
                    }
                    @Override
                    public int getBatchSize() {
                        return bowl.size();
                    }
                });
        bowl.clear();
    }

}

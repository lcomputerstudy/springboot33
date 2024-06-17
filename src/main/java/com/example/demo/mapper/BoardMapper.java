package com.example.demo.mapper;

import com.example.demo.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    public List<Board> selectBoardList();
}

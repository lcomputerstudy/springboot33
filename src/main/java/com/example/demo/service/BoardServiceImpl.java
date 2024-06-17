package com.example.demo.service;

import com.example.demo.domain.Board;
import com.example.demo.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired BoardMapper boardMapper;

    @Override
    public List<Board> selectBoardList() {
        return boardMapper.selectBoardList();
    }
}

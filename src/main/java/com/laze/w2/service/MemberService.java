package com.laze.w2.service;

import com.laze.w2.dao.MemberDAO;
import com.laze.w2.domain.MemberVO;
import com.laze.w2.dto.MemberDTO;
import com.laze.w2.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

@Log4j2
public enum MemberService {
    INSTANCE;

    private MemberDAO memberDAO;
    private ModelMapper modelMapper;

    MemberService() {

        memberDAO = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get();

    }

    public MemberDTO login(String mid, String mpw) throws Exception {

        MemberVO memberVO = memberDAO.getWithPassword(mid, mpw);

        MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);

        return memberDTO;

    }

    public void updateUuid(String mid, String uuid) throws Exception {

        memberDAO.updateUuid(mid, uuid);

    }

    public MemberDTO getByUuid(String uuid) throws Exception {

        MemberVO memberVO = memberDAO.selectUuid(uuid);

        MemberDTO memberDTO = modelMapper.map(memberVO, MemberDTO.class);

        return memberDTO;
    }

}

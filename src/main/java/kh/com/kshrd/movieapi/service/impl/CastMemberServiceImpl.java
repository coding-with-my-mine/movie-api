package kh.com.kshrd.movieapi.service.impl;

import kh.com.kshrd.movieapi.dto.request.CastMemberRequest;
import kh.com.kshrd.movieapi.exception.NotFoundException;
import kh.com.kshrd.movieapi.model.CastMember;
import kh.com.kshrd.movieapi.repository.CastMemberRepository;
import kh.com.kshrd.movieapi.service.CastMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CastMemberServiceImpl implements CastMemberService {

    private final CastMemberRepository castMemberRepository;

    @Override
    public CastMember create(CastMemberRequest request) {
        return castMemberRepository.create(request);
    }

    @Override
    public List<CastMember> getAll(Integer page, Integer size) {
        page = (page - 1) * size;
        return castMemberRepository.getAll(page, size);
    }

    @Override
    public CastMember getById(Long castMemberId) {
        CastMember castMember = castMemberRepository.getById(castMemberId);
        if (castMember == null) {
            throw new NotFoundException("Cast member not found");
        }
        return castMember;
    }

    @Override
    public List<CastMember> getByMovieId(Long movieId) {
        return castMemberRepository.getByMovieId(movieId);
    }

    @Override
    public CastMember update(Long castMemberId, CastMemberRequest request) {
        getById(castMemberId);
        return castMemberRepository.update(castMemberId, request);
    }

    @Override
    public void delete(Long castMemberId) {
        getById(castMemberId);
        castMemberRepository.delete(castMemberId);
    }
}

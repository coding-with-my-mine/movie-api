package kh.com.kshrd.movieapi.service;

import kh.com.kshrd.movieapi.dto.request.CastMemberRequest;
import kh.com.kshrd.movieapi.model.CastMember;

import java.util.List;

public interface CastMemberService {
    CastMember create(CastMemberRequest request);

    List<CastMember> getAll(Integer page, Integer size);

    CastMember getById(Long castMemberId);

    List<CastMember> getByMovieId(Long movieId);

    CastMember update(Long castMemberId, CastMemberRequest request);

    void delete(Long castMemberId);
}

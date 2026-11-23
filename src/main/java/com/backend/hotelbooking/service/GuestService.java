package com.backend.hotelbooking.service;

import com.backend.hotelbooking.dto.request.GuestRequest;
import com.backend.hotelbooking.dto.response.GuestResponse;
import com.backend.hotelbooking.entity.Guest;
import com.backend.hotelbooking.exception.DuplicateResourceException;
import com.backend.hotelbooking.exception.ResourceNotFoundException;
import com.backend.hotelbooking.mapper.GuestMapper;
import com.backend.hotelbooking.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;

    @Transactional
    public GuestResponse create(GuestRequest request){
        if (guestRepository.existsByEmailIgnoreCase(request.email())){
            throw new DuplicateResourceException("Guest", request.email());
        }

        Guest guest = guestMapper.toEntity(request);
        Guest savedGuest = guestRepository.save(guest);
        return guestMapper.toResponse(savedGuest);
    }

    @Transactional(readOnly = true)
    public Page<GuestResponse> findAll(Pageable pageable){
        return guestRepository.findAll(pageable).map(guestMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public GuestResponse findById(Long guestId){
        return guestMapper.toResponse(findGuestOrThrow(guestId));
    }

    @Transactional
    public GuestResponse update(Long guestId, GuestRequest request){
        Guest guest = findGuestOrThrow(guestId);
        if (guestRepository.existsByEmailIgnoreCaseAndIdNot(request.email(), guestId)){
            throw new DuplicateResourceException("Guest", request.email());
        }

        guest.setFullName(request.fullName());
        guest.setEmail(request.email());
        guest.setPhone(request.phone());
        return guestMapper.toResponse(guest);
    }

    @Transactional
    public void deleteById(Long guestId){
        guestRepository.delete(findGuestOrThrow(guestId));
    }

    private Guest findGuestOrThrow(Long guestId){
        return guestRepository.findById(guestId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", guestId));
    }
}

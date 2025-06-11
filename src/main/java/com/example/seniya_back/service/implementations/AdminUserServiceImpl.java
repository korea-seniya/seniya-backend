//package com.example.seniya_back.service.implementations;
//
//import com.example.seniya_back.common.constants.ResponseCode;
//import com.example.seniya_back.common.constants.ResponseMessage;
//import com.example.seniya_back.dto.ResponseDto;
//import com.example.seniya_back.dto.admin.user.response.GetAllUserResponseDto;
//import com.example.seniya_back.dto.admin.user.response.GetUserDetailRespDto;
//import com.example.seniya_back.entity.User;
//import com.example.seniya_back.repository.CourseRepository;
//import com.example.seniya_back.repository.PassRepository;
//import com.example.seniya_back.repository.PaymentRepository;
//import com.example.seniya_back.repository.UserRepository;
//import com.example.seniya_back.service.AdminUserService;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class AdminUserServiceImpl implements AdminUserService {
//
//    private final UserRepository userRepository;
//    private final PaymentRepository paymentRepository;
//    private final PassRepository passRepository;
//    private final CourseRepository courseRepository;
//
//    @Override
//    public ResponseDto<List<GetAllUserResponseDto>> getAllUser() {
//        List<GetAllUserResponseDto> respDto = null;
//
//        List<User> users = userRepository.findAll();
//
//        respDto = users.stream().map(user -> GetAllUserResponseDto.builder()
//                .user(user)
//                .build()
//        ).collect(Collectors.toList());
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDto).getBody();
//    }
//
//    @Override
//    public ResponseDto<GetUserDetailRespDto> getUserById(long id) {
//
//        GetUserDetailRespDto respDto = null;
//
//        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));
//
//        respDto = GetUserDetailRespDto.builder()
//                .name(user.getName())
//                .phone(user.getPhone())
//                .roleName(user.getRole().toString())
//                .couponCount(paymentRepository.findByUser(user))
//                .availablePasses(passRepository.findByUser(user))
//                .courses(courseRepository.findAllByUser(user))
//                .build();
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDto).getBody();
//    }
//}

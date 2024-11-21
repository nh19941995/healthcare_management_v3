package org.example.healthcare_management_v2.map;

import org.example.healthcare_management_v2.dto.UserDto;
import org.example.healthcare_management_v2.dto.auth.RegisterDto;
import org.example.healthcare_management_v2.dto.userDto.UpdateUserDto;
import org.example.healthcare_management_v2.dto.userDto.UserUpdateDto;
import org.example.healthcare_management_v2.dto.userDto.UserWithDoctorDto;
import org.example.healthcare_management_v2.entities.User;
import org.example.healthcare_management_v2.map.helper.UserMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                RoleMapper.class,
                DoctorMapper.class,
                UserMapperHelper.class
        } // bảo đảm MapStruct biết cách sử dụng RoleMapper cho các ánh xạ giữa Role và RoleDto
)
public interface UserMapper {

    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "avatar", source = "user.username", qualifiedByName = "getAvatarForUser")
    UserDto userToUserDto(User user);

    // ánh xạ từ UserDto sang User (tạo mới)
    User RegisterDtoToUser(RegisterDto registerDto);

    // cập nhật thông tin user từ RegisterDto (cập nhật)
    void updateUserFromRegisterDto(RegisterDto RegisterDto, @MappingTarget User user);

    // cập nhật từ UpdateUserDto sang User (cập nhật)
    void updateUserFromUpdateUserDto(UpdateUserDto UpdateUserDto, @MappingTarget User user);


    @Mapping(target = "avatar", source = "user.username", qualifiedByName = "getAvatarForUser")
    UserWithDoctorDto userToUserWithDoctorDto(User user);

    // cập nhật thông tin user từ UserUpdateDto (cập nhật)
    void updateUserFromUserUpdateDto(UserUpdateDto userUpdateDto, @MappingTarget User user);
}

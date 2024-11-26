package torra.queue.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import torra.queue.controller.dto.AdminDto;
import torra.queue.entity.Admin;
import torra.queue.repository.AdminRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService {

    public final AdminRepository adminRepository;

    public final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminService(AdminRepository adminRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminRepository = adminRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Admin createAdmin(AdminDto adminDto){
        Admin admin = new Admin(
                null,
                adminDto.name(),
                adminDto.lastName(),
                adminDto.email(),
                null,
                bCryptPasswordEncoder.encode(adminDto.password())
        );
        return adminRepository.save(admin);
    }

    public Optional<Admin> getAdminById(String id){
        return adminRepository.findById(UUID.fromString(id));
    }

    public List<Admin> listAdmin(){
        return adminRepository.findAll();
    }

    public Boolean updateById(String id,
                              AdminDto adminDto){
        var entity = adminRepository.findById(UUID.fromString(id));
        if (entity.isEmpty()){
            return false;
        }
        if (adminDto.name()!=null){
            entity.get().setName(adminDto.name());
        }
        if (adminDto.lastName()!=null){
            entity.get().setLastName(adminDto.lastName());
        }
        if (adminDto.password()!=null){
            entity.get().setPassword(bCryptPasswordEncoder.encode(adminDto.password()));
        }
        if (adminDto.email()!=null){
            entity.get().setEmail(adminDto.email());
        }
        return true;
    }

    public Boolean deleteById(String id){
        var entity = adminRepository.findById(UUID.fromString(id));
        if (entity.isEmpty()){
            return false;
        }
        adminRepository.deleteById(UUID.fromString(id));
        return true;
    }
}

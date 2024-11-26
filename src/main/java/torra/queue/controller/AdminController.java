package torra.queue.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import torra.queue.controller.dto.AdminDto;
import torra.queue.controller.dto.ClientDto;
import torra.queue.controller.dto.LoginRequest;
import torra.queue.controller.dto.LoginResponse;
import torra.queue.entity.Admin;
import torra.queue.entity.Client;
import torra.queue.service.AdminService;
import torra.queue.service.ClientService;
import torra.queue.service.TokenService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminDto adminDto){
        var admin = adminService.createAdmin(adminDto);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<Optional> getAdminById(@PathVariable("adminId") String adminId){
        var admin = adminService.getAdminById(adminId);
        if(admin.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(admin);
    }

    @GetMapping
    public ResponseEntity<List<Admin>> listClient(){
        return ResponseEntity.ok(adminService.listAdmin());
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<Void> updateClientById(@PathVariable("adminId")String adminId,
                                                 @RequestBody AdminDto adminDto){
        if (adminService.updateById(adminId, adminDto)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdminById(@PathVariable("adminId")String adminId){
        if (adminService.deleteById(adminId)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

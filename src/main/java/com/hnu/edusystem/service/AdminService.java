package com.hnu.edusystem.service;

import com.hnu.edusystem.domain.Admin;
import com.hnu.edusystem.exception.EduException;
import com.hnu.edusystem.exception.EnumExceptions;
import com.hnu.edusystem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @Author: WaveLee
 * @Date: 2018/6/5 22:16
 */
@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    /**
     * 新增
     *
     * @param admin
     * @return
     */
    public Admin save(Admin admin) {

        // 验证是否存在
        if (admin == null || (admin.getId() != null && adminRepository.findOne(admin.getId()) != null)) {
            throw new EduException(EnumExceptions.ADD_FAILED_DUPLICATE);
        }

        return adminRepository.save(admin);
    }

    /**
     * 更新
     *
     * @param admin
     * @return
     */
    public Admin update(Admin admin) {

        // 验证是否存在
        if (admin == null || admin.getId() == null || adminRepository.findOne(admin.getId()) == null) {
            throw new EduException(EnumExceptions.UPDATE_FAILED_NOT_EXIST);
        }

        return adminRepository.save(admin);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(String id) {

        // 验证是否存在
        if (adminRepository.findOne(id) == null) {
            throw new EduException(EnumExceptions.DELETE_FAILED_NOT_EXIST);
        }
        adminRepository.delete(id);
    }

    /**
     * 批量删除
     *
     * @param admins
     */
    public void deleteInBatch(Collection<Admin> admins) {
        adminRepository.deleteInBatch(admins);
    }

    /**
     * 通过编码查询
     *
     * @param id
     * @return
     */
    public Admin findOne(String id) {
        return adminRepository.findOne(id);
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    /**
     * 查询所有-分页
     *
     * @param page
     * @param size
     * @param sortFieldName
     * @param asc
     * @return
     */
    public Page<Admin> findAllByPage(Integer page, Integer size, String sortFieldName, Integer asc) {

        // 判断排序字段名是否存在
        try {
            Admin.class.getDeclaredField(sortFieldName);
        } catch (Exception e) {
            // 如果不存在就设置为id
            sortFieldName = "id";
        }

        Sort sort = null;
        if (asc == 0) {
            sort = new Sort(Sort.Direction.DESC, sortFieldName);
        } else {
            sort = new Sort(Sort.Direction.ASC, sortFieldName);
        }

        Pageable pageable = new PageRequest(page, size, sort);
        return adminRepository.findAll(pageable);
    }
}

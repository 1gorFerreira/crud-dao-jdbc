package main;

import java.util.List;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Demo2 {
    public static void main(String[] args){
        DepartmentDao depDao = DaoFactory.createDepartmentDao();
        
        System.out.println("=== Test 1: seller findById ===");
        Department dep = depDao.findById(1);
        System.out.println(dep);
        
        System.out.println("\n=== Test 2: department findAll");
        List<Department> list = depDao.findAll();
        for(Department x : list){
            System.out.println(x);
        }
        
        System.out.println("\n=== Test 3: department insert");
        Department depto = new Department(null, "Gamer");
        depDao.insert(depto);
        System.out.println("Inserte completed! New Id = " + depto.getId());
        
        System.out.println("\n=== Test 4: department update");
        Department depto1 = depDao.findById(4);
        depto1.setName("Esportes");
        depDao.update(depto1);
        System.out.println("Update completed");
        
        System.out.println("\n=== Test 5: department delete");
        depDao.deleteById(9);
        depDao.deleteById(10);
        depDao.deleteById(11);
        System.out.println("Delete complete");
    }
}

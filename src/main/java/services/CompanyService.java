package services;

import java.util.List;

import javax.ejb.Stateless;

import entities.Car;
import entities.Company;

@Stateless
public class CompanyService extends BaseService {

   public List<Company> getAllCompanies() {
      List<Company> companies = entityManager.createNamedQuery("Company.findAll", Company.class).getResultList();
      return companies;
   }

   public List<Car> getAllCarsByCompany(Company company) {
      return company.getCars();
   }

}

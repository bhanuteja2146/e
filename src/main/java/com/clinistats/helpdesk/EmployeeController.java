package com.clinistats.helpdesk;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	EmployeeServie employeeServie;

	@RequestMapping(value = "/vl/employee/add", method = RequestMethod.POST)
	public Employee addEmployee(@Valid @RequestBody Employee request) {
		Employee employee = employeeServie.add(request);
		return employee;
	}

	@RequestMapping(value = "/vl/employee/tax-deduction", method = RequestMethod.POST)
	public List<EmployeeTaxResponse> getTaxDeduction() {
		List<EmployeeTaxResponse> employees = employeeServie.getTaxDeduction();
		getTaxAmount(employees);
		return employees;
	}

	private void getTaxAmount(List<EmployeeTaxResponse> employees) {
		LocalDate today = LocalDate.now();
		LocalDate start = LocalDate.of(today.getYear(), Month.APRIL, 1);

		employees.stream().forEach(a -> {

			boolean dojMonth = a.getDoj().isAfter(start) ? true : false;
			if (dojMonth) {
				boolean firstDateJoining = a.getDoj().getDayOfMonth() == 1 ? true : false;
				if (firstDateJoining) {
					int months = 13 - a.getDoj().getDayOfMonth() + 3;
					a.setYearlySalry(a.getSalary().multiply(new BigDecimal(months)));
				} else {
					int day = a.getDoj().getDayOfMonth();
					int days = 30 - day > 0 ? 30 - day : 0;
					int months = 12 - a.getDoj().getMonthValue() + 3;
					BigDecimal dailySalary = a.getSalary().divide(new BigDecimal("30"), 3, RoundingMode.HALF_UP); // Assuming
																													// 30
																													// days
					BigDecimal salaryForDays = dailySalary.multiply(new BigDecimal(days));
					a.setYearlySalry(a.getSalary().multiply(new BigDecimal(months)).add(salaryForDays));
				}
			} else {
				a.setYearlySalry(a.getSalary().multiply(new BigDecimal("12")));

			}

			if (a.getYearlySalry().compareTo(new BigDecimal("250000")) > 0
					&& a.getYearlySalry().compareTo(new BigDecimal("500000")) <= 0) {
				a.setTaxAmount(a.getYearlySalry().subtract(new BigDecimal("250000")).multiply(new BigDecimal("0.05")));
				a.setAccesAmount(new BigDecimal("0"));
			} 
			else if (a.getYearlySalry().compareTo(new BigDecimal("500000")) > 0
					&& a.getYearlySalry().compareTo(new BigDecimal("1000000")) <= 0) {
				a.setTaxAmount(a.getYearlySalry().subtract(new BigDecimal("500000")).multiply(new BigDecimal("0.1"))
						.add(new BigDecimal("12500")));
				a.setAccesAmount(new BigDecimal("0"));
			} else if (a.getYearlySalry().compareTo(new BigDecimal("1000000")) > 0) {
				if (a.getYearlySalry().compareTo(new BigDecimal("2500000")) > 0) {
					a.setAccesAmount(
							a.getYearlySalry().subtract(new BigDecimal("2500000")).multiply(new BigDecimal("0.02")));
				} else {
					a.setAccesAmount(new BigDecimal("0"));
				}
				a.setTaxAmount(a.getYearlySalry().subtract(new BigDecimal("1000000")).multiply(new BigDecimal("0.2"))
						.add(new BigDecimal("37500")));
			} else {
				a.setTaxAmount(new BigDecimal("0"));
				a.setAccesAmount(new BigDecimal("0"));
			}
		});
	}

}

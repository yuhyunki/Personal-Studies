package com.greedy.emp;

import static com.greedy.common.JDBCTemplate.getConnection;
import static com.greedy.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import com.greedy.model.dto.EmployeeDTO;

public class EmployeeDAO {

	/* 구현 조건
	 * 1. Connection 생성은 JDBC 템플릿 사용
	 * 2. query문은 xml파일로 분리
	 * 3. 쿼리문에 값을 전달해야 하는 경우는 PreparedStatement, 아닌 경우는 Statement 사용
	 * 4. 한 행 정보는 DTO에 담아 출력, 여러 행 정보는 ArrayList에 담아 출력
	 *  */

	public void findOneEmpByEmpId(String inputEmpId) {

		/* 사번으로 직원정보 조회 후 EmployeeDTO에 담아 출력 */
		Connection con = getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;

		EmployeeDTO selectedEmp = null;

		Properties prop = new Properties();

		try {
			prop.loadFromXML(new FileInputStream("src/com/greedy/emp/employee-query.xml"));

			String query = prop.getProperty("selectEmpByEmpId");

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, inputEmpId);

			rset = pstmt.executeQuery();

			if(rset.next()) {
				selectedEmp = new EmployeeDTO();

				selectedEmp.setEmpId(rset.getString("EMP_ID"));
				selectedEmp.setEmpName(rset.getString("EMP_NAME"));
				selectedEmp.setEmpNo(rset.getString("EMP_NO"));
				selectedEmp.setEmail(rset.getString("EMAIL"));
				selectedEmp.setPhone(rset.getString("PHONE"));
				selectedEmp.setDeptCode(rset.getString("DEPT_CODE"));
				selectedEmp.setJobCode(rset.getString("JOB_CODE"));
				selectedEmp.setSalLevel(rset.getString("SAL_LEVEL"));
				selectedEmp.setSalary(rset.getInt("SALARY"));
				selectedEmp.setBonus(rset.getDouble("BONUS"));
				selectedEmp.setManagerId(rset.getString("MANAGER_ID"));
				selectedEmp.setHireDate(rset.getDate("HIRE_DATE"));
				selectedEmp.setEntDate(rset.getDate("ENT_DATE"));
				selectedEmp.setEntYn(rset.getString("ENT_YN"));
			}

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			close(con);
		}

		System.out.println(selectedEmp);
	}

	public void viewAllEmp() {

		Connection con = getConnection();

		Statement stmt = null;
		ResultSet rset = null;

		List<EmployeeDTO> empList = null;

		Properties prop = new Properties();

		try {
			prop.loadFromXML(new FileInputStream("src/com/greedy/emp/employee-query.xml"));

			String query = prop.getProperty("selectAllEmp");

			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			empList = new ArrayList<>();

			while(rset.next()) {

				EmployeeDTO row = new EmployeeDTO();

				row.setEmpId(rset.getString("EMP_ID"));
				row.setEmpName(rset.getString("EMP_NAME"));
				row.setEmpNo(rset.getString("EMP_NO"));
				row.setEmail(rset.getString("EMAIL"));
				row.setPhone(rset.getString("PHONE"));
				row.setDeptCode(rset.getString("DEPT_CODE"));
				row.setJobCode(rset.getString("JOB_CODE"));
				row.setSalLevel(rset.getString("SAL_LEVEL"));
				row.setSalary(rset.getInt("SALARY"));
				row.setBonus(rset.getDouble("BONUS"));
				row.setManagerId(rset.getString("MANAGER_ID"));
				row.setHireDate(rset.getDate("HIRE_DATE"));
				row.setEntDate(rset.getDate("ENT_DATE"));
				row.setEntYn(rset.getString("ENT_YN"));

				empList.add(row);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
			close(con);
		}

		for(EmployeeDTO emp : empList) {
			System.out.println(emp);
		}
	}

	public void findEmpByGender(char inputEmpGender) {

		Connection con = getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;

		List<EmployeeDTO> empList = null;
		
		Properties prop = new Properties();

		try {
			prop.loadFromXML(new FileInputStream("src/com/greedy/emp/employee-query.xml"));
			
			String query = prop.getProperty("findEachGender");
			

			pstmt = con.prepareStatement(query);
			
			if(inputEmpGender == '남') {
				pstmt.setString(1, "1");
			} else if(inputEmpGender == '여') {
				pstmt.setString(1, "2");
			}

			rset = pstmt.executeQuery();

			empList = new ArrayList<>();

			while(rset.next()) {

				EmployeeDTO row = new EmployeeDTO();

				row.setEmpId(rset.getString("EMP_ID"));
				row.setEmpName(rset.getString("EMP_NAME"));
				row.setEmpNo(rset.getString("EMP_NO"));
				row.setEmail(rset.getString("EMAIL"));
				row.setPhone(rset.getString("PHONE"));
				row.setDeptCode(rset.getString("DEPT_CODE"));
				row.setJobCode(rset.getString("JOB_CODE"));
				row.setSalLevel(rset.getString("SAL_LEVEL"));
				row.setSalary(rset.getInt("SALARY"));
				row.setBonus(rset.getDouble("BONUS"));
				row.setManagerId(rset.getString("MANAGER_ID"));
				row.setHireDate(rset.getDate("HIRE_DATE"));
				row.setEntDate(rset.getDate("ENT_DATE"));
				row.setEntYn(rset.getString("ENT_YN"));

				empList.add(row);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			close(con);
		}

		for(EmployeeDTO emp : empList) {
			System.out.println(emp);
		}
	}

	public void sortEmpBySalaryAsc() {

		Connection con = getConnection();

		Statement stmt = null;
		ResultSet rset = null;

		List<EmployeeDTO> empList = null;

		Properties prop = new Properties();

		try {
			prop.loadFromXML(new FileInputStream("src/com/greedy/emp/employee-query.xml"));

			String query = prop.getProperty("sortEmpBySalaryAsc");

			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			empList = new ArrayList<>();

			while(rset.next()) {

				EmployeeDTO row = new EmployeeDTO();

				row.setEmpId(rset.getString("EMP_ID"));
				row.setEmpName(rset.getString("EMP_NAME"));
				row.setEmpNo(rset.getString("EMP_NO"));
				row.setEmail(rset.getString("EMAIL"));
				row.setPhone(rset.getString("PHONE"));
				row.setDeptCode(rset.getString("DEPT_CODE"));
				row.setJobCode(rset.getString("JOB_CODE"));
				row.setSalLevel(rset.getString("SAL_LEVEL"));
				row.setSalary(rset.getInt("SALARY"));
				row.setBonus(rset.getDouble("BONUS"));
				row.setManagerId(rset.getString("MANAGER_ID"));
				row.setHireDate(rset.getDate("HIRE_DATE"));
				row.setEntDate(rset.getDate("ENT_DATE"));
				row.setEntYn(rset.getString("ENT_YN"));

				empList.add(row);
			}

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
			close(con);
		}

		for(EmployeeDTO emp : empList) {
			System.out.println(emp);
		}
	}

	public void sortEmpBySalaryDesc() {

		Connection con = getConnection();

		Statement stmt = null;
		ResultSet rset = null;

		List<EmployeeDTO> empList = null;

		Properties prop = new Properties();

		try {
			prop.loadFromXML(new FileInputStream("src/com/greedy/emp/employee-query.xml"));

			String query = prop.getProperty("sortEmpBySalaryDesc");

			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			empList = new ArrayList<>();

			while(rset.next()) {

				EmployeeDTO row = new EmployeeDTO();

				row.setEmpId(rset.getString("EMP_ID"));
				row.setEmpName(rset.getString("EMP_NAME"));
				row.setEmpNo(rset.getString("EMP_NO"));
				row.setEmail(rset.getString("EMAIL"));
				row.setPhone(rset.getString("PHONE"));
				row.setDeptCode(rset.getString("DEPT_CODE"));
				row.setJobCode(rset.getString("JOB_CODE"));
				row.setSalLevel(rset.getString("SAL_LEVEL"));
				row.setSalary(rset.getInt("SALARY"));
				row.setBonus(rset.getDouble("BONUS"));
				row.setManagerId(rset.getString("MANAGER_ID"));
				row.setHireDate(rset.getDate("HIRE_DATE"));
				row.setEntDate(rset.getDate("ENT_DATE"));
				row.setEntYn(rset.getString("ENT_YN"));

				empList.add(row);
			}

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
			close(con);
		}

		for(EmployeeDTO emp : empList) {
			System.out.println(emp);
		}

	}

	public void sortEmpByHireDateAsc() {

		Connection con = getConnection();

		Statement stmt = null;
		ResultSet rset = null;

		List<EmployeeDTO> empList = null;

		Properties prop = new Properties();

		try {
			prop.loadFromXML(new FileInputStream("src/com/greedy/emp/employee-query.xml"));

			String query = prop.getProperty("sortEmpByHireDateAsc");

			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			empList = new ArrayList<>();

			while(rset.next()) {

				EmployeeDTO row = new EmployeeDTO();

				row.setEmpId(rset.getString("EMP_ID"));
				row.setEmpName(rset.getString("EMP_NAME"));
				row.setEmpNo(rset.getString("EMP_NO"));
				row.setEmail(rset.getString("EMAIL"));
				row.setPhone(rset.getString("PHONE"));
				row.setDeptCode(rset.getString("DEPT_CODE"));
				row.setJobCode(rset.getString("JOB_CODE"));
				row.setSalLevel(rset.getString("SAL_LEVEL"));
				row.setSalary(rset.getInt("SALARY"));
				row.setBonus(rset.getDouble("BONUS"));
				row.setManagerId(rset.getString("MANAGER_ID"));
				row.setHireDate(rset.getDate("HIRE_DATE"));
				row.setEntDate(rset.getDate("ENT_DATE"));
				row.setEntYn(rset.getString("ENT_YN"));

				empList.add(row);
			}

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
			close(con);
		}

		for(EmployeeDTO emp : empList) {
			System.out.println(emp);
		}

	}

	public void sortEmpByHireDateDesc() {

		Connection con = getConnection();

		Statement stmt = null;
		ResultSet rset = null;

		List<EmployeeDTO> empList = null;

		Properties prop = new Properties();

		try {
			prop.loadFromXML(new FileInputStream("src/com/greedy/emp/employee-query.xml"));

			String query = prop.getProperty("sortEmpByHireDateDesc");

			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			empList = new ArrayList<>();

			while(rset.next()) {

				EmployeeDTO row = new EmployeeDTO();

				row.setEmpId(rset.getString("EMP_ID"));
				row.setEmpName(rset.getString("EMP_NAME"));
				row.setEmpNo(rset.getString("EMP_NO"));
				row.setEmail(rset.getString("EMAIL"));
				row.setPhone(rset.getString("PHONE"));
				row.setDeptCode(rset.getString("DEPT_CODE"));
				row.setJobCode(rset.getString("JOB_CODE"));
				row.setSalLevel(rset.getString("SAL_LEVEL"));
				row.setSalary(rset.getInt("SALARY"));
				row.setBonus(rset.getDouble("BONUS"));
				row.setManagerId(rset.getString("MANAGER_ID"));
				row.setHireDate(rset.getDate("HIRE_DATE"));
				row.setEntDate(rset.getDate("ENT_DATE"));
				row.setEntYn(rset.getString("ENT_YN"));

				empList.add(row);
			}

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
			close(con);
		}

		for(EmployeeDTO emp : empList) {
			System.out.println(emp);
		}

	}

	public void findEmpTop5Salary() {

		Connection con = getConnection();

		Statement stmt = null;
		ResultSet rset = null;

		List<EmployeeDTO> empList = null;

		Properties prop = new Properties();

		try {
			prop.loadFromXML(new FileInputStream("src/com/greedy/emp/employee-query.xml"));

			String query = prop.getProperty("findEmpTop5Salary");

			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			empList = new ArrayList<>();

			while(rset.next()) {

				EmployeeDTO row = new EmployeeDTO();

				row.setEmpId(rset.getString("EMP_ID"));
				row.setEmpName(rset.getString("EMP_NAME"));
				row.setEmpNo(rset.getString("EMP_NO"));
				row.setEmail(rset.getString("EMAIL"));
				row.setPhone(rset.getString("PHONE"));
				row.setDeptCode(rset.getString("DEPT_CODE"));
				row.setJobCode(rset.getString("JOB_CODE"));
				row.setSalLevel(rset.getString("SAL_LEVEL"));
				row.setSalary(rset.getInt("SALARY"));
				row.setBonus(rset.getDouble("BONUS"));
				row.setManagerId(rset.getString("MANAGER_ID"));
				row.setHireDate(rset.getDate("HIRE_DATE"));
				row.setEntDate(rset.getDate("ENT_DATE"));
				row.setEntYn(rset.getString("ENT_YN"));

				empList.add(row);
			}

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
			close(con);
		}

		for(EmployeeDTO emp : empList) {
			System.out.println(emp);
		}
		
	}

	public void findEmpTop6to10Salary() {

		Connection con = getConnection();

		Statement stmt = null;
		ResultSet rset = null;

		List<EmployeeDTO> empList = null;

		Properties prop = new Properties();

		try {
			prop.loadFromXML(new FileInputStream("src/com/greedy/emp/employee-query.xml"));

			String query = prop.getProperty("findEmpTop6to10Salary");

			stmt = con.createStatement();
			rset = stmt.executeQuery(query);

			empList = new ArrayList<>();

			while(rset.next()) {

				EmployeeDTO row = new EmployeeDTO();

				row.setEmpId(rset.getString("EMP_ID"));
				row.setEmpName(rset.getString("EMP_NAME"));
				row.setEmpNo(rset.getString("EMP_NO"));
				row.setEmail(rset.getString("EMAIL"));
				row.setPhone(rset.getString("PHONE"));
				row.setDeptCode(rset.getString("DEPT_CODE"));
				row.setJobCode(rset.getString("JOB_CODE"));
				row.setSalLevel(rset.getString("SAL_LEVEL"));
				row.setSalary(rset.getInt("SALARY"));
				row.setBonus(rset.getDouble("BONUS"));
				row.setManagerId(rset.getString("MANAGER_ID"));
				row.setHireDate(rset.getDate("HIRE_DATE"));
				row.setEntDate(rset.getDate("ENT_DATE"));
				row.setEntYn(rset.getString("ENT_YN"));

				empList.add(row);
			}

		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
			close(con);
		}

		for(EmployeeDTO emp : empList) {
			System.out.println(emp);
		}
		
	}

}

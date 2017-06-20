/** 
 * JmsToolkitWebApplication.
 * Copyright (C) 2017 Gabelopment (gabelopment@gmail.com)
 * 
 * This file is part of JmsToolkitWebApplication.
 * 
 * JmsToolkitWebApplication is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JmsToolkitWebApplication is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with JmsToolkitWebApplication.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.example.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class for the Spring Boot Application.
 * @author Gabriel
 *
 */
@SpringBootApplication
public class JmsToolkitWebApplication {

	/**
	 * Starts the Spring Boot application.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(JmsToolkitWebApplication.class, args);
	}
}

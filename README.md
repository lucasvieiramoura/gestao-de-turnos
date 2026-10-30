# 📅 Sistema de Gestão de Turnos
> Sistema de gestão completo para gestão de turnos de profisionais, permitindo aprovações de ausências e regras para não exceder limite de execução de horas e plantões

<p align="center">
  <img src="https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=openjdk" alt="Java" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=springboot" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white" alt=Docker>
  <img src="https://img.shields.io/badge/Keycloack-IAM-blue?style=for-the-badge&logo=keycloak&color=%234D4D4Dalt="Keycloak">
  <img src="https://img.shields.io/badge/Flayway-DB_Migration-red?style=for-the-badge&logo=flyway&color=%23CC0200" alt="Flayway">
  <img src="https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge&logo=postgresql" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white" alt="Hibernate">
  <img src="https://img.shields.io/badge/Status-Em_Desenvolviemento-blue?style=for-the-badge" alt="Em Desenvolvimetno" />
</p>

---

## 📋 Sobre o Projeto

A **Gestão de Turnos** é uma aplicação full-stack desenvolvida para gerir o ecossistema completo de plantões e escalas de horários, englobando controle de plantões executados pelo funcionários de forma que não exceda 24 horas seguida ou 2 plantões de 12 horas, os funcionários consulta a escala semanal de turno pelo aplicaivo Mobile e podem solicitar ausência programadas, sendo necessária aprovação do Gerente que será executada em nossa versão Web que possibilitará administração completa dos turnos, profisionais e horários.

---

## 🚀 Tecnologias Utilizadas

### **Infra**
* **Docker** ( Execução do projeto através de containers)
* **Flayway** (Administração e versionamento das tabelas do Banco)
* **Keycloack** (Open source para IAM - Identity and Access Managment, Gestão de controle de acesso. )

### **Backend**
* **Java 17+** / **Spring Boot 3.x**
* **Spring Security** (Autenticação Básica e Controlo de Papéis / Roles)
* **Spring Data JPA / Hibernate** (Hibernate apenas para valida as entidade e tabelas)
* **PostgreSQL**

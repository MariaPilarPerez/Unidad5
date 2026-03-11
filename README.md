# UD05 - Tarea Obligatoria RA4
## Crear y gestionar ObjectDB

### Descripción
Gestión de una pequeña empresa con ObjectDB (SGBDOO puro).

### Modelo de datos
- **Empleado**: nombre, fechaContrato, malaActuacion (no persistir), Direccion embebida
- **Departamento**: nombre, sede, colección de empleados (1-N)
- **Proyecto**: descripcion, directorProyecto (1-1), empleados participantes (N-M)
- **Direccion**: calle, numero, bloque (clase embebida)

### Tareas a realizar

#### 1. Completar las anotaciones JPA en las entidades

| Clase | Anotaciones necesarias |
|-------|----------------------|
| `Direccion.java` | `@Embeddable` |
| `Empleado.java` | `@Entity`, `@Id`, `@GeneratedValue`, `@Transient`, `@Embedded`, `@ManyToOne`, `@ManyToMany` |
| `Departamento.java` | `@Entity`, `@Id`, `@GeneratedValue`, `@OneToMany` |
| `Proyecto.java` | `@Entity`, `@Id`, `@GeneratedValue`, `@OneToOne`, `@ManyToMany` |

#### 2. Implementar getters, setters y métodos auxiliares
- `addEmpleado()`, `addProyecto()`, `removeEmpleado()`
- `toString()` en todas las entidades

#### 3. Completar las 5 consultas JPQL en App.java

1. **Proyectos sin empleados** - `size(p.losEmpleados)=0`
2. **Empleados sin departamento** - `e.departamento is null`
3. **Empleados de un año concreto** - filtrar por año de `fechaContrato`
4. **Empleado más antiguo** - ordenar por fecha ASC, limitar a 1
5. **Empleados con número de proyectos** - recorrer y contar `losProyectos.size()`

### Estructura del proyecto
```
UD05_Obligatorio_RA4/
├── pom.xml
├── EmpleadosDepartamentoProyecto.odb
└── src/main/java/com/ad/
    ├── App.java
    ├── model/
    │   ├── Direccion.java
    │   ├── Empleado.java
    │   ├── Departamento.java
    │   └── Proyecto.java
    └── util/
        ├── Connection.java
        └── Leer.java
```

### Ejecución
1. Abrir carpeta en VS Code
2. Maven descargará las dependencias automáticamente
3. Ejecutar `App.java`

### Recordatorios
- Todo programa necesita un buen diseño del que partir
- Comprobar los modos de almacenamiento de cada dato
- Evitar recursividades en las adiciones entre entidades recíprocas"# UD05_Obligatorio_RA4" 
"# Accesoadatos_tema5" 
"# UD05_Obligatorio_RA4" 
"# UD05_Obligatorio_RA4" 
"# Unidad5" 

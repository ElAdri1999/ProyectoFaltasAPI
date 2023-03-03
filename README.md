# API Rest

### INDICE
1. [INTRODUCCIÓN](#introducción)
2. [METODOS GENERALES](#metodos-generales)
3. [METODOS CONCRETOS](#metodos-concretos)
   * [Metodo Login](#metodo-login)
   * [Metodo Devolver un Profesor con una Mascara de Datos](#metodo-devolver-un-profesor-con-una-mascara-de-datos)
   * [Metodo Crear un Aviso de Guardia](#metodo-crear-un-aviso-de-guardia)
   * [Metodo Para Recibir las Guardias Dentro de un Horario](#metodo-para-recibir-las-guardias-dentro-de-un-horario)
   * [Metodo Anular Guardia](#metodo-anular-guardia)
   * [Metodo Para Autocompletar los Formularios de la APP de Escritorio](#metodo-para-autocompletar-los-formularios-de-la-app-de-escritorio)


### INTRODUCCIÓN

La API esta enlazada a las aplicaciones mediante funciones de uso genereal o,
algunas funciones de uso mas concreto para hacer funcionar los metodos de las apps.

---

### METODOS GENERALES

Los metodos generales son los que te devuelven todos los datos de una columna de la base de datos,
no tienen mucha complejidad, son utiles pero tiene la desventaja de que en estos metodos la app es la que tiene que hacer las funciones,
haciendo que las app funcionen mas lento e incluso puedan llegar a colgarse.

#### *EJEMPLO*
```java
    @GetMapping("/profesores")
    public ResponseEntity<?> obtenerTodos() {
        List<Profesor> profesores = profesorRepositorio.findAll();
        if (profesores.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(profesores);
    }
```
---
### METODOS CONCRETOS

Dentro de la API tambien hemos incluido metodos mas concretos para poder alijerar el peso de computación de la app,
haciendo que la propia API sea la que devuelva los campos necesarios en la app, acontinuacion dejo algunos ejemplos:


#### *METODO LOGIN*

Este metodo recibe de la app el usuario y la contraseña y devuelve los datos del usuario introducido.

#### *EJEMPLO*
```java
    @PostMapping("/login")
    public ResponseEntity<?> validateUser(@RequestHeader("user") String usuario, @RequestHeader("pswd") String contra) {
        try {
            TypedQuery<Profesor> q = em.createQuery("SELECT p FROM Profesor p WHERE p.user = :user AND p.password = :pswd", Profesor.class);
            q.setParameter("user", usuario);
            q.setParameter("pswd", contra);
            
            List<Profesor> profesor = q.getResultList();
            if (!profesor.isEmpty()) {
                return ResponseEntity.ok(profesor);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado");
            }
        } catch (NoResultException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del sistema");
        }
    }
```
---
#### *METODO DEVOLVER UN PROFESOR CON UNA MASCARA DE DATOS*

Este metodo recibe el id del usuario dentro de su url y devuelve una lista de objetos profesor con unos datos concretos,
ya que son los que se necesitan en la app y es innecesario enviar todos los demas datos.

#### *EJEMPLO*
```java
    @GetMapping("/profesores-datos/{id}")
    public ResponseEntity<?> obtenerDatosUno(@PathVariable Integer id) {
        Profesor profesor = profesorRepositorio.findById(id).orElse(null);
        if (profesor == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(profesor.getId() + "," + profesor.getNombre() + "," + profesor.getApe1() + ","
                + profesor.getApe2() + "," + profesor.getDni());
    }
```

#### *METODO CREAR UN AVISO DE GUARDIA* 

Este metodo se usa en la app para crear los avisos de guardias enviando a la API el aviso mas la APIKey del usuario registrado,
la APIKey se pide para evitar que se hagan avisos desde fuera de la app.

#### *EJEMPLO*
```java
    @PostMapping("/crear-aviso")
    public ResponseEntity<?> crearAviso(@RequestBody AvisoGuardia aviso, @RequestHeader("key") String key) {
        if(!AppFaltasApiServiceApplication.validarKey(key)) return null;
        
        AvisoGuardia nuevo = avisoGuardiaRepositorio.save(aviso);
        Guardia nuevaGuardia = new Guardia();
        nuevaGuardia.setProf_falta(aviso.getProfesor());
        nuevaGuardia.setEstado("C");
        nuevaGuardia.setFecha(aviso.getFecha_guardia());
        nuevaGuardia.setHorario(aviso.getHorario());
        
        Horario h = horarioRepositorio.findById(aviso.getHorario()).orElse(null);
        nuevaGuardia.setDia_semana(h.getDia_semana());
        nuevaGuardia.setHora(h.getHora());
        nuevaGuardia.setGrupo(h.getGrupo());
        nuevaGuardia.setAula(h.getAula());
        
        nuevaGuardia.setAviso(nuevo.getId_aviso());
        nuevaGuardia.setObservaciones(aviso.getObservaciones());
        
        guardiaRepositorio.save(nuevaGuardia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
```
---
#### *METODO PARA RECIBIR LAS GUARDIAS DENTRO DE UN HORARIO* 
Igual que en los demas metodos ha de recibir la APIKey para poder devolver los datos, esto se hace como metodo de seguridad,
una vez comprobado los datos devuelve las guardias de ese horario.

#### *EJEMPLO*
```java
    @GetMapping("/guardias-por-horario/{id}")
    public ResponseEntity<?> guardiasPorHorario(@PathVariable String id, @RequestHeader("key") String key) {
        if(!AppFaltasApiServiceApplication.validarKey(key)) return null;
        
        TypedQuery<Object[]> q = em.createQuery("SELECT h.dia_semana, h.hora_guardia FROM HorarioGuardia h WHERE h.profesor = :id", Object[].class);
        q.setParameter("id", id);
        List<Object[]> horarios = new ArrayList<>();
        if(q.isBound(q.getParameter("id"))) {
            try {
            horarios = q.getResultList();
            }catch(Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la petición");
            }
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en la petición");
        }
        List<Guardia> guardias = new ArrayList<>();
        TypedQuery<Guardia> q2 = em.createQuery("SELECT g FROM Guardia g WHERE g.fecha = current_date and g.hora = :h and g.dia_semana = :d and g.estado = 'C'", Guardia.class);
        if(!horarios.isEmpty()) {
            horarios.forEach(h -> {
                q2.setParameter("h", h[1]);
                q2.setParameter("d", h[0]);
                
                guardias.addAll(q2.getResultList());
            });
        }
        
        if(!guardias.isEmpty()) return ResponseEntity.ok(guardias);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sin resultados");
    }
```
---
#### *METODO ANULAR GUARDIA* 

Este metodo recibe una guardia y un id para poder borrarla de la base de datos.

#### *EJEMPLO*
```java
    @PutMapping("anular-guardia/{id}")
    public ResponseEntity<?> anularGuardia(@RequestBody Guardia guardia, @PathVariable int id, @RequestHeader("key") String key) {
        if(!AppFaltasApiServiceApplication.validarKey(key)) return null;
        
        Guardia g = guardiaRepositorio.findById(id).orElse(null);
        if(g == null) {
            return ResponseEntity.notFound().build();
        }
        
        g.setEstado("A");
        if(g.getAviso() != null) {
            AvisoGuardia a = avisoGuardiaRepositorio.findById(g.getAviso()).orElse(null);
            if(a != null) {
                a.setFecha_guardia(null);
                a.setFecha_hora_aviso(LocalDateTime.now());
                a.setConfirmado(false);
                a.setAnulado(true);
                
                avisoGuardiaRepositorio.save(a);
            }
        }
        
        return ResponseEntity.ok(guardiaRepositorio.save(g));
    }
```
---


#### *METODO PARA AUTOCOMPLETAR LOS FORMULARIOS DE LA APP DE ESCRITORIO*

Este metodo recibe los campos de nombre apellidos fecha y hora para poder devolver el resto de campos para su uso en la app de escritorio.

#### *EJEMPLO*
 ```java
        @PostMapping("/completar-guardias")
        public ResponseEntity<?> autoCompletarGuardias(@RequestHeader("nombre") String nombre, @RequestHeader("apellidos") String apellidos, @RequestHeader("fecha") String fecha, @RequestHeader("hora") String hora) {
            try {
                String[] apellidoSeparado = apellidos.split(" ");
                String ape1 = apellidoSeparado[0];
                String ape2 = apellidoSeparado[1];
                TypedQuery<Horario> q = em.createQuery("SELECT h FROM Horario h JOIN Profesor p ON h.profesor = p.id WHERE p.nombre = :nombre AND p.ape1 = :ape1 AND p.ape2 = :ape2 AND h.dia_semana = :dia AND h.hora = :hora", Horario.class);
                q.setParameter("nombre", nombre);
                q.setParameter("ape1", ape1);
                q.setParameter("ape2", ape2);
                LocalDate fech = LocalDate.parse(fecha);
                int dia = fech.getDayOfWeek().getValue();
                q.setParameter("dia", dia);
                q.setParameter("hora", hora);			
                List<Horario> horario = q.getResultList();
                if (!horario.isEmpty()) {
                    return ResponseEntity.ok(horario);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acceso denegado");
                }
            } catch (NoResultException ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error del sistema");
            }
        }
```

Drop database if exists dbtonyskinal2016405;
create database DBTonysKinal2016405;
use DBTonysKinal2016405;
delimiter &&
create procedure Empresas()
begin
create table Empresas(codigoEmpresa int not null auto_increment primary key,
			 nombreEmpresa varchar(150) not null,
             direccion varchar(150) not null,
             telefono varchar(10) not null);
end &&
delimiter ;
delimiter &&
create procedure Servicios()
begin
create table Servicios(codigoServicios int not null auto_increment primary key,
			 fechaServicios date not null,
             tipoServicios varchar(100) not null,
             horaServicio time not null,
             lugarSevicio varchar(100) not null,
             telefonoContacto varchar(10) not null,
             Empresas_codigoEmpresa int not null,
foreign key (Empresas_codigoEmpresa) references Empresas(codigoEmpresa)on delete cascade);
end &&
delimiter ;
delimiter &&
create procedure Presupuesto()
begin
create table Presupuesto(codigoPresupuesto int not null auto_increment primary key,
			 fechaSolicitud date,
             cantidadPresupuesto decimal(10,2) not null,
			 Empresas_codigoEmpresa int not null,
             foreign key (Empresas_codigoEmpresa) references Empresas(codigoEmpresa)on delete cascade);
end &&
delimiter ;
delimiter &&
create Procedure TipoPlato()
begin
create table TipoPlato(codigoTipoPlato int not null auto_increment primary key,
			 descriccion varchar(100) not null);
end &&
delimiter ;
delimiter &&
create procedure Platos()
begin
create table Platos(codigoPlato int not null auto_increment primary key, 
			 cantidad int not null, 
             nombrePlato varchar(50) not null, 
             descripcionPlato varchar(100) not null,
             precioPlato decimal(10,2) not null,
             TipoPlato_codigoTipoPlato int not null,
             foreign key (TipoPlato_codigoTipoPlato) references TipoPlato(codigoTipoPlato)on delete cascade);
end &&
delimiter ;
delimiter &&
create procedure Servicios_has_Platos()
begin
create table Servicios_has_Platos(Servicios_CodigoServicio int ,
			Platos_codigoPlato int,
foreign key (Servicios_CodigoServicio) references Servicios(codigoServicios)on delete cascade,
foreign key (Platos_codigoPlato) references Platos(codigoPlato)on delete cascade);
end &&
delimiter ;
delimiter &&
create procedure Productos()
begin
create table Productos(codigoProductos int not null auto_increment primary key,
			nombreProducto varchar(150) not null,
            cantidad int not null);
end &&
delimiter ;

delimiter &&
create procedure Productos_has_Platos()
begin 
create table Productos_has_Platos(Productos_codigoProducto int,
									Platos_codigoPlato int,
									foreign key(Productos_codigoProducto) references Productos(codigoProductos)on delete cascade,
                                    foreign key(Platos_codigoPlato) references Platos(codigoPlato)on delete cascade);
end &&
delimiter ;
delimiter &&
create procedure TipoEmpleado()
begin
create table TipoEmpleado(codigoTipoEmpleado int not null auto_increment primary key, 
						descripcion varchar(100)not null);
end &&
delimiter ;
delimiter &&
create procedure Empleados()
begin 
create table Empleados(codigoEmpleado int not null auto_increment primary key, 
			 apellidosEmpleado varchar(150) not null,
             nombresEmpleado varchar(150) not null, 
             direccionEmpleado varchar(150)not null,
             telefonoContacto varchar(10) not null,
             gradoCocinero varchar(50),
             TipoEmpleado_codigoTipoEmpleado int not null,
             foreign key (TipoEmpleado_codigoTipoEmpleado)  references TipoEmpleado(codigoTipoEmpleado)on delete cascade); 
end &&
delimiter ;

delimiter &&
create procedure Servicios_has_Empleados()
begin
create table Servicios_has_Empleados(codigo int not null auto_increment primary key,Servicios_CodigoServicios int ,Empleados_codigoEmpleado int,
foreign key (Servicios_CodigoServicios) references Servicios(codigoServicios)on delete cascade,
foreign key (Empleados_codigoEmpleado) references Empleados(codigoEmpleado)on delete cascade,
fecha date, horeEvento time, lugarEvento varchar(150));
end &&
delimiter ;
call Empresas();
call Presupuesto();
call Servicios();
call TipoPlato();
call Productos();
call Platos();
call TipoEmpleado();
call Empleados();
call Productos_has_Platos();
call Servicios_has_Platos();
call Servicios_has_Empleados();
#========Agregar===================
delimiter &&
create procedure sp_AgregarTipoEmpleado(in descr varchar(100))
begin
insert into tipoempleado(descripcion) values(descr);
end &&
delimiter ;
delimiter &&
create procedure sp_AgregarTipoPlato(in descr varchar(100))
begin
insert into tipoplato(descriccion) values(descr);
end &&
delimiter ;
delimiter &&
create procedure sp_AgregarEmpresas(in descr varchar(150),in direccion varchar(150),in telefono varchar(10))
begin
insert into empresas(nombreEmpresa,direccion,telefono) values(descr,direccion,telefono);
end &&
delimiter ;
delimiter &&
create procedure sp_AgregarServicios(in fecha date,in tipoServicios varchar(100),in horaServicio time,in lugarServicio varchar(100),
in telefonoContacto varchar(10),in empresascodigo int)
begin
insert into servicios(fechaServicios,tipoServicios,horaServicio,lugarSevicio,telefonoContacto,Empresas_codigoEmpresa) 
values(fecha,tipoServicios,horaServicio,lugarServicio,telefonoContacto,empresascodigo);
end &&
delimiter ;
delimiter &&
create procedure sp_AgregarPresupuesto(in descr date,in tipoServicios decimal(10,2),in horaServicio int)
begin
insert into presupuesto(fechaSolicitud,cantidadPresupuesto,Empresas_codigoEmpresa) 
values(descr,tipoServicios,horaServicio);
end &&
delimiter ;
delimiter &&
create procedure sp_AgregarPlatos(in descr int,in tipoServicios varchar(50),in horaServicio varchar(100),in precio decimal(10,2),in plato int)
begin
insert into platos(cantidad,nombrePlato,descripcionPlato,precioPlato,TipoPlato_codigoTipoPlato) 
values(descr,tipoServicios,horaServicio,precio,plato);
end &&
delimiter ;
delimiter &&
create procedure sp_AgregarServicios_has_Platos(in descr int,in tipoServicios int)
begin
insert into servicios_has_platos(Servicios_CodigoServicio,Platos_codigoPlato) 
values(descr,tipoServicios);
end &&
delimiter ;
delimiter &&
create procedure sp_AgregarProductos(in descr varchar(150),in tipoServicios int)
begin
insert into productos(nombreProducto,cantidad) 
values(descr,tipoServicios);
end &&
delimiter ;

delimiter &&
create procedure sp_AgregarProductos_has_Platos(in descr int,in tipoServicios int)
begin
insert into productos_has_platos(Productos_codigoProducto,Platos_codigoPlato) 
values(descr,tipoServicios);
end &&
delimiter ;

delimiter &&
create procedure sp_AgregarEmpleados(in apelli varchar(150),in nom varchar(150),in dire varchar(150),in tel varchar(10),in grado varchar(50),in codigoempleado int)
begin
insert into empleados(apellidosEmpleado,nombresEmpleado,direccionEmpleado,telefonoContacto,gradoCocinero,TipoEmpleado_codigoTipoEmpleado) 
values(apelli,nom,dire,tel,grado,codigoempleado);
end &&
delimiter ;
delimiter &&
create procedure sp_AgregarServicios_has_Empleados(in descr int,in apelli int,in fecha date,in hora time,in lugar varchar(150))
begin
insert into Servicios_has_Empleados(Servicios_CodigoServicios,Empleados_codigoEmpleado,fecha,horeEvento,lugarEvento) 
values(descr,apelli,fecha,hora,lugar);
end &&
delimiter ;
call sp_AgregarTipoEmpleado("Secretario");
call sp_AgregarTipoEmpleado("Jefe");
call sp_AgregarTipoEmpleado("Asistente");
call sp_AgregarTipoEmpleado("Oficinista");
call sp_AgregarTipoEmpleado("Vendedor");
call sp_AgregarTipoPlato("Entrada");
call sp_AgregarTipoPlato("Sopa");
call sp_AgregarTipoPlato("Plato Fuerte");
call sp_AgregarTipoPlato("Postre");
call sp_AgregarTipoPlato("Bebida");
call sp_AgregarEmpresas("Office Depot","6ta avenida zona 5","52809250");
call sp_AgregarEmpresas("Intelaf","4ta avenida zona 8","52809375");
call sp_AgregarEmpresas("Amicelco","5ta avenida zona 1","93847103");
call sp_AgregarEmpresas("La Torre","7ta avenida zona 4","84672903");
call sp_AgregarEmpresas("La Barata","10ma avenida zona 8","93769250");
call sp_AgregarEmpresas("Farmacias Batres","4ta avenida zona 7","98316487");
call sp_AgregarEmpresas("Farmacias Cruz Verde","4ta avenida zona 2","34781467");
call sp_AgregarEmpresas("Claro","3ra avenida zona 3","37456871");
call sp_AgregarEmpresas("Nestle","7ta avenida zona 4","35784168");
call sp_AgregarEmpresas("Coca-cola","6ta avenida zona 8","46871327");
call sp_AgregarEmpresas("Ingrup","10ma avenida zona 9","65713548");
call sp_AgregarEmpresas("Praisa","1a avenida zona 2","78143568");
call sp_AgregarEmpresas("Tigo Enterprise","3a avenida zona 4","42146789");
call sp_AgregarEmpresas("Telefonica","22a avenida zona 8","14328468");
call sp_AgregarEmpresas("Banco G&T","13a avenida zona 11","12346578");
call sp_AgregarEmpresas("EMPROCER","17a avenida zona 16","45678134");
call sp_AgregarEmpresas("Telsec","20a avenida zona 22","67146578");
call sp_AgregarEmpresas("Microsoft","1a avenida zona 13","64217682");
call sp_AgregarEmpresas("Nokia","13a avenida zona 8","65475932");
call sp_AgregarEmpresas("Santillana","24a avenida zona 17","64788612");
call sp_AgregarServicios('2020/04/3',"Servicio de almuezo en conferencia",'14:00',"Hotel camino real","42494516",1);
call sp_AgregarServicios('2020/04/7',"Servicio de desayuno",'07:00',"Salon para eventos","98276301",2);
call sp_AgregarServicios('2020/04/10',"Servicio de refaccion",'15:00',"Area de almuerzos de la empresa","72651903",3);
call sp_AgregarServicios('2020/04/15',"Servicio de refaccion",'10:00',"Parque de la industria, sala 1","81740392",4);
call sp_AgregarServicios('2020/04/20',"Servicio de Churrasco",'12:00',"Salon Cumunal","87392071",5);
call sp_AgregarServicios('2020/07/25',"Servicio de desayuno",'10:00',"Empresa dada","42494550",1);

call sp_AgregarPresupuesto('2020/03/25',8500,1);
call sp_AgregarPresupuesto('2020/03/28',10000,2);
call sp_AgregarPresupuesto('2020/03/30',8000,3);
call sp_AgregarPresupuesto('2020/04/01',9000,4);
call sp_AgregarPresupuesto('2020/04/04',11000,5);
call sp_AgregarPresupuesto('2020/05/29',10000,1);
call sp_AgregarPlatos(150,"Boloñeza","Espagueti con salsa y carne",20.50,1);
call sp_AgregarPlatos(150,"Caribeña","Sopa de Mariscos",50,2);
call sp_AgregarPlatos(150,"Alas Agridulces","Alas de pollo bañadas en salsa agridulce",15.50,3);
call sp_AgregarPlatos(150,"Pastel","Pastel de chocolate con nueces",7.50,4);
call sp_AgregarPlatos(150,"Jamaica","Fresco de jamaica con un toque de limon",4.50,5);
call sp_AgregarServicios_has_Platos(1,1);
call sp_AgregarServicios_has_Platos(1,2);
call sp_AgregarServicios_has_Platos(1,3);
call sp_AgregarServicios_has_Platos(1,4);
call sp_AgregarServicios_has_Platos(1,5);
call sp_AgregarServicios_has_Platos(2,3);
call sp_AgregarServicios_has_Platos(2,4);
call sp_AgregarServicios_has_Platos(3,2);
call sp_AgregarServicios_has_Platos(4,4);
call sp_AgregarServicios_has_Platos(4,5);
call sp_AgregarServicios_has_Platos(5,3);
call sp_AgregarServicios_has_Platos(5,5);
call sp_AgregarProductos("Tenedores",1500);
call sp_AgregarProductos("Cucharas",1500);
call sp_AgregarProductos("Vasos",1500);
call sp_AgregarProductos("Platos Planos",1500);
call sp_AgregarProductos("Platos Hondos",1500);
call sp_AgregarEmpleados("Mejilla","Isaac","Zona 7","3545642","Cheff",1);
call sp_AgregarEmpleados("Garcia","Byron","Zona 6","56789123","Cocinero",2);
call sp_AgregarEmpleados("Escobar","Axel","Zona 5","84620918","Mesero",3);
call sp_AgregarEmpleados("Barrios","Paola","Zona 4","73946203","Concinero",4);
call sp_AgregarEmpleados("Castillo","Helena","Zona 2","63947502","Cheff",5);
call sp_AgregarProductos_has_Platos(1,1);
call sp_AgregarProductos_has_Platos(4,1);
call sp_AgregarProductos_has_Platos(2,2);
call sp_AgregarProductos_has_Platos(5,2);
call sp_AgregarProductos_has_Platos(4,3);
call sp_AgregarProductos_has_Platos(2,4);
call sp_AgregarProductos_has_Platos(4,4);
call sp_AgregarProductos_has_Platos(3,5);
call sp_AgregarServicios_has_Empleados(1,1,'2020/04/5','14:00',"Hotel caminoreal");
call sp_AgregarServicios_has_Empleados(1,3,'2020/04/5','14:00',"Hotel caminoreal");
call sp_AgregarServicios_has_Empleados(1,2,'2020/04/5','14:00',"Hotel caminoreal");
call sp_AgregarServicios_has_Empleados(2,5,'2020/04/7','07:00',"Salon para eventos");
call sp_AgregarServicios_has_Empleados(2,4,'2020/04/7','07:00',"Salon para eventos");
call sp_AgregarServicios_has_Empleados(2,3,'2020/04/7','07:00',"Salon para eventos");
call sp_AgregarServicios_has_Empleados(3,2,'2020/04/10','15:00',"Area de almuerzos de la empresa");
call sp_AgregarServicios_has_Empleados(3,5,'2020/04/10','15:00',"Area de almuerzos de la empresa");
call sp_AgregarServicios_has_Empleados(3,4,'2020/04/10','15:00',"Area de almuerzos de la empresa");
call sp_AgregarServicios_has_Empleados(4,2,'2020/04/15','10:00',"Parque de la industria, sala 1");
call sp_AgregarServicios_has_Empleados(4,1,'2020/04/15','10:00',"Parque de la industria, sala 1");
call sp_AgregarServicios_has_Empleados(4,3,'2020/04/15','10:00',"Parque de la industria, sala 1");
call sp_AgregarServicios_has_Empleados(5,4,'2020/04/20','12:00',"Salon Cumunal");
call sp_AgregarServicios_has_Empleados(5,1,'2020/04/20','12:00',"Salon Cumunal");
call sp_AgregarServicios_has_Empleados(5,2,'2020/04/20','12:00',"Salon Cumunal");
#==========Listar=================
delimiter &&
create procedure sp_ListarTipoEmpleado()
begin
select tipoempleado.codigoTipoEmpleado, 
	   tipoempleado.descripcion
				from tipoempleado;
end &&
delimiter ;

delimiter &&
create procedure sp_ListarEmpresas()
begin
select 
		empresas.codigoEmpresa,
        Empresas.nombreEmpresa,
        Empresas.direccion,
        Empresas.telefono
				from Empresas;
end &&
delimiter ;

delimiter &&
create procedure sp_ListarEmpleados()
begin
select 
		empleados.codigoEmpleado,
        empleados.apellidosEmpleado, 
        empleados.nombresEmpleado,
        empleados.direccionEmpleado,
        empleados.telefonoContacto,
        empleados.gradoCocinero,
        empleados.TipoEmpleado_codigoTipoEmpleado
				from empleados;
end &&
delimiter ;

delimiter &&
create procedure sp_ListarPlatos()
begin
select 
		platos.codigoPlato,
        platos.cantidad,
        platos.nombrePlato,
        platos.descripcionPlato,
        platos.precioPlato,
        platos.TipoPlato_codigoTipoPlato
				from platos;
end &&
delimiter ;

delimiter &&
create procedure sp_ListarPresupuestos()
begin
select 
		presupuesto.codigoPresupuesto,
        presupuesto.fechaSolicitud,
        presupuesto.cantidadPresupuesto,
        presupuesto.Empresas_codigoEmpresa
			from presupuesto;
end &&
delimiter ;

delimiter &&
create procedure sp_ListarProductos()
begin
select 
		productos.codigoProductos,
        productos.nombreProducto,
        productos.cantidad
			from productos;
end &&
delimiter ;

delimiter &&
create procedure sp_ListarServicios()
begin
select 
		servicios.codigoServicios,
        servicios.fechaServicios,
        servicios.tipoServicios,
        servicios.horaServicio,
        servicios.lugarSevicio,
        servicios.telefonoContacto,
        servicios.Empresas_codigoEmpresa
			from servicios;
end &&
delimiter ;

delimiter &&
create procedure sp_ListarTipoPlato()
begin
select 
		tipoplato.codigoTipoPlato,
        tipoplato.descriccion
			from tipoplato;
end &&
delimiter ;

delimiter &&
create procedure sp_ListarServiciosHasEmpleados()
begin
select
	servicios_has_empleados.codigo,
	servicios_has_empleados.Servicios_CodigoServicios,
    servicios_has_empleados.Empleados_codigoEmpleado,
    servicios_has_empleados.fecha,
    servicios_has_empleados.horeEvento,
    servicios_has_empleados.lugarEvento
			from servicios_has_empleados;
end &&
delimiter ;

delimiter &&
create procedure sp_ListarProductosHasPlatos()
begin
	select
			productos_has_platos.Productos_codigoProducto,productos_has_platos.Platos_codigoPlato
				from productos_has_platos;
end &&
delimiter ;

delimiter &&
create procedure sp_ReportePresupuesto(in codEmpresa int)
begin
select * from empresas E inner join servicios S on 
	E.codigoEmpresa = S.Empresas_codigoEmpresa where 
		E.codigoEmpresa=codEmpresa;
	
end &&
delimiter ;

delimiter &&
create procedure sp_SubReportePresupuesto(in codEmpresa int)
begin
	select * from empresas E inner join Presupuesto P on
	E.codigoEmpresa = P.Empresas_codigoEmpresa where E.codigoEmpresa=codEmpresa
		Group by P.cantidadPresupuesto;
end &&
delimiter ;

delimiter &&
create procedure sp_ListarServiciosHasPlatos()
begin
select servicios_has_platos.Servicios_CodigoServicio,servicios_has_platos.Platos_codigoPlato
			from servicios_has_platos;
end &&
delimiter ;

delimiter &&
create procedure sp_ListarSubReporteFinal(in codServicio int)
begin
select * from servicios S inner join servicios_has_platos SP on
		S.codigoServicios=SP.Servicios_CodigoServicio 
        inner join platos P on
			SP.Platos_codigoPlato=P.codigoPlato
		inner join productos_has_platos PP on
			P.codigoPlato=PP.Productos_codigoProducto
		inner join productos PR on 
			PP.Platos_codigoPlato=PR.codigoProductos
        where S.codigoServicios=codServicio  group by PR.nombreProducto;
        end &&
delimiter ;

delimiter &&
create procedure sp_ListarReporteFinal(in codServicio int)
begin
	select * from servicios S inner join servicios_has_platos SP on
		S.codigoServicios=SP.Servicios_CodigoServicio 
        inner join platos P on
			SP.Platos_codigoPlato=P.codigoPlato
		inner join tipoplato TP on
			P.TipoPlato_codigoTipoPlato=TP.codigoTipoPlato
        where S.codigoServicios=codServicio;

end &&
delimiter ; 
#========actualizar===================
delimiter &&
create procedure sp_ActualizarEmpresas(in id int,in descr varchar(150),in direccion varchar(150),in telefono varchar(10))
begin
update empresas set nombreEmpresa=descr,direccion=direccion,telefono=telefono where codigoEmpresa=id;
end &&
delimiter ;

delimiter &&
create procedure sp_ActualizarServicios(in id int,in fecha date,in tipoServicios varchar(100),in horaServicio time,in lugarServicio varchar(100),
in telefonoContacto varchar(10))
begin
update servicios set fechaServicios=fecha,tipoServicios=tipoServicios,horaServicio=horaServicio,lugarSevicio=lugarServicio,
telefonoContacto=telefonoContacto where codigoServicios=id;
end &&
delimiter ;

delimiter &&
create procedure sp_ActualizarPresupuesto(in id int,in descr date,in tipoServicios decimal(10,2))
begin
update presupuesto set fechaSolicitud=descr,cantidadPresupuesto=tipoServicios where codigoPresupuesto=id;
end &&
delimiter ;

delimiter &&
create procedure sp_ActualizarPlatos(in id int,in descr int,in tipoServicios varchar(50),in horaServicio varchar(100),in precio decimal(10,2))
begin
update platos set cantidad=descr,nombrePlato=tipoServicios,descripcionPlato=horaServicio,precioPlato=precio
where codigoPlato=id;
end &&
delimiter ;

delimiter &&
create procedure sp_ActualizarProductos(in id int,in descr varchar(150),in tipoServicios int)
begin
update productos set nombreProducto=descr,cantidad=tipoServicios 
where codigoProductos=id;
end &&
delimiter ;

delimiter &&
create procedure sp_ActualizarEmpleados(in id int,in apelli varchar(150),in nom varchar(150),in dire varchar(150),
in tel varchar(10),in grado varchar(50))
begin
update empleados set apellidosEmpleado=apelli,nombresEmpleado=nom,direccionEmpleado=dire,
telefonoContacto=tel,gradoCocinero=grado where codigoEmpleado=id;
end &&
delimiter ;

delimiter &&
create procedure sp_ActualizarTipoEmpleado(in id int,in descrip varchar(100))
begin
update tipoempleado set descripcion=descrip where codigoTipoEmpleado=id;
end &&
delimiter ;

delimiter && 
create procedure sp_ActualizrTipoPlato(in id int, in descrip varchar(100))
begin
update tipoplato set descriccion=descrip where codigoTipoPlato=id;
end &&
delimiter ;

delimiter &&
create procedure sp_ActualizrServiciosHasEmpleados(in cod int,in fecha date, in hora time, in lugar varchar(100))
begin
update servicios_has_empleados set fecha=fecha, horeEvento=hora, lugarEvento=lugar where codigo=cod;
end &&
delimiter ;
#=========Eliminar================
delimiter &&
create procedure sp_Eliminarproductos_has_platos(in id int)
begin
delete from productos_has_platos where Productos_codigoProducto=id;
end &&
delimiter ;
delimiter &&
create procedure sp_Eliminarservicios_has_platos(in id int)
begin
delete from servicios_has_platos where Servicios_CodigoServicio=id;
end &&
delimiter ;
delimiter &&
create procedure sp_Eliminarservicios_has_empleados(in id int)
begin
delete from servicios_has_empleados where Servicios_CodigoServicios=id;
end &&
delimiter ;
delimiter &&
create procedure sp_EliminarEmpresas(in id int)
begin
delete from empresas where codigoEmpresa=id;
end &&
delimiter ;
delimiter &&
create procedure sp_EliminarServicios(in id int)
begin
delete from servicios where codigoServicios=id  ;
end &&
delimiter ;
delimiter &&
create procedure sp_EliminarPresupuesto(in id int)
begin
delete from presupuesto where codigoPresupuesto=id;
end &&
delimiter ;
delimiter &&
create procedure sp_EliminarPlatos(in id int)
begin
delete from platos where codigoPlato=id;
end &&
delimiter ;
delimiter &&
create procedure sp_EliminarProductos(in id int)
begin
delete from productos where codigoProductos=id;
end &&
delimiter ;
delimiter &&
create procedure sp_EliminarEmpleados(in id int)
begin
delete from empleados where codigoEmpleado=id;
end &&
delimiter ;
delimiter &&
create procedure sp_EliminarTipoPlato(in id int)
begin
delete from tipoplato where codigoTipoPlato=id;
end &&
delimiter ;

delimiter &&
create procedure sp_EliminarTipoEmpleado(in id int)
begin
delete from tipoempleado where codigoTipoEmpleado=id;
end &&
delimiter ;
#=======Buscar=========
delimiter &&
create procedure sp_Buscarproductos_has_platos(in id int)
begin
select * from productos_has_platos where Productos_codigoProducto=id;
end &&
delimiter ;
delimiter &&
create procedure sp_Buscarservicios_has_platos(in id int)
begin
select * from servicios_has_platos where Servicios_CodigoServicio=id;
end &&
delimiter ;
delimiter &&
create procedure sp_Buscarservicios_has_empleados(in id int)
begin
select * from servicios_has_empleados where Servicios_CodigoServicios=id;
end &&
delimiter ;
delimiter &&
create procedure sp_BuscarEmpresas(in id int)
begin
select * from empresas where codigoEmpresa=id;
end &&
delimiter ;
delimiter &&
create procedure sp_BuscarServicios(in id int)
begin
select * from servicios where codigoServicios=id  ;
end &&
delimiter ;
delimiter &&
create procedure sp_BuscarPresupuesto(in id int)
begin
select * from presupuesto where codigoPresupuesto=id;
end &&
delimiter ;
delimiter &&
create procedure sp_BuscarPlatos(in id int)
begin
select * from platos where codigoPlato=id;
end &&
delimiter ;
delimiter &&
create procedure sp_BuscarProductos(in id int)
begin
select * from productos where codigoProductos=id;
end &&
delimiter ;
drop procedure 
delimiter &&
create procedure sp_BuscarEmpleados(in id int)
begin
select * from empleados where codigoEmpleado=id;
end &&
delimiter ;
delimiter &&
create procedure sp_BuscarTipoPlato(in id int)
begin
select * from tipoplato where codigoTipoPlato=id;
end &&
delimiter ;
delimiter &&
create procedure sp_BuscarTipoEmpleado(in id int)
begin
select * from tipoempleado where codigoTipoEmpleado=id;
end &&
delimiter ;

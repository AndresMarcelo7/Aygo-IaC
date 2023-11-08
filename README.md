# WEB APP AUTOMATICALLY DEPLOYED TO AWS

Aqui se encuentra el codigo para desplegar infraestructura (3 maquinas de EC2 con un servicio basico) en AWS, usando CDK.

## Requerimientos

* Java 8 o superior
* Maven
* AWS CLI configurado con permisos de administrador
* Credenciales de AWS configuradas en el sistema (aws configure)
* CDK instalado (npm install -g aws-cdk)
* jq instalado (Linux: sudo apt-get install jq, Mac: brew install jq, Windows: https://stedolan.github.io/jq/download/)
* git instalado

## Ejecucion

0. Clonar el repositorio
1. En el archivo [CdkProjectApp.java](src%2Fmain%2Fjava%2Fcom%2Fmyorg%2FCdkProjectApp.java) Cambiar los valores de la VPC y el Rol de EC2 por los valores de la cuenta de AWS en la que se va a desplegar la infrastructura.
2. Ejecutar el script [deploy.sh](deploy.sh) con el comando `./deploy.sh`
3. Esperar a que termine el despliegue (puede tardar unos minutos en crear las maquinas)
4. Ir a la consola de AWS y en el servicio de EC2 buscar las direcciones IP publicas de las maquinas creadas
5. Ingresar a las direcciones IP publicas de las maquinas en el navegador (protocolo HTTP) y verificar que se despliega la aplicacion (`http://[IP_PUBLICA]/hello`)
6. Para destruir la infraestructura, ejecutar el script [destroy.sh](destroy.sh) con el comando `./destroy.sh`

## Evidencia

* Cloudformation Stack
![CloudformationResources.png](img%2FCloudformationResources.png)
* Ec2 Desplegadas
![Ec2Console.png](img%2FEc2Console.png)
* Aplicacion desplegada en las maquinas
  * maquina 1
  ![ServiceDeployed.png](img%2FServiceDeployed.png)
  * maquina 2
  ![ServiceDeployed2.png](img%2FServiceDeployed2.png)
  * maquina 3
  ![ServiceDeployed3.png](img%2FServiceDeployed3.png)
* User data de las maquinas:
![Ec2UserData.png](img%2FEc2UserData.png)

## Limitaciones

Debido a las restricciones en los roles IAM en la cuenta de vocareum, no se pudo realizar el `cdk bootstrap` ni el `cdk deploy` por lo cual se tuvo que hacer un script que ejecutara los comandos de forma manual.

## Conclusiones
* Se logro desplegar la infraestructura en AWS usando CDK y se logro desplegar la aplicacion en las maquinas creadas.
* Se logro automatizar el despliegue de la infraestructura usando un script de bash (tambien es posible con cdk deploy).
* Se logro automatizar la destruccion de la infraestructura usando un script de bash (tambien es posible con cdk destroy).
* El uso de IaC es muy util para desplegar infraestructura de forma rapida y repetible.
* CDK es una herramienta muy util para desplegar infraestructura en AWS, ya que permite usar lenguajes de programacion para definir la infraestructura.
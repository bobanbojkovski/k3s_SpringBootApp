# build and deploy spring boot app to [k3s](https://k3s.io/)

### Prerequisites

* Installed Docker to build and push an image to registry.
* [Installed and configured kubernetes](https://github.com/bobanbojkovski/k3s) - [k3s](https://k3s.io/) cluster.


### Demo uses Makefile to build and deploy spring boot app and mysql db to kubernetes

Sample Spring Boot application demo prints out strings, username & password.

http://dev.example.com/users/list
```
id	5
username	"distracted_tesla"
password	"jofndhhXia2KIzq7LmgPsancWqVAlceC"
```

Use **make env=environment build_target** syntax to trigger Makefile target, for example:<br/>
```
make env=dev docker_build docker_push
make env=dev deploy  
```

Environment variables are defined in dev.env, test.env files and mapped in template configurations placed under **templates** directory.

Update path and <node_name> in persistent volume file:

```
hostPath:
    path: /volumes/${NS}
  nodeAffinity:
    required:
      nodeSelectorTerms:
      - matchExpressions:
        - key: kubernetes.io/hostname
          operator: In
          values:
          - <node_name>
```

**docker_build:** target in Makefile triggers instructions defined in Dockerfile. Maven image is used to run mvn package command then openjdk image defines ENTRYPOINT for the demo application.

**build_files** target prepares environment specific deployments in **deployments** directory.

```
build_files:
	$(info prepare deploy files)
	@mkdir -p $(BUILDS_DIR)/$(env)
	@for file in $(shell find $(TEMPLATES_DIR) -type f -name '*.yaml' | sed 's#.*/##' | sort ) ; do \
		$(SHELL_EXPORT) envsubst '$$NS,$$H' < $(TEMPLATES_DIR)/$$file > $(BUILDS_DIR)/$(env)/$$file ;\
	done
```

**deploy** target applies the configurations in sequenced order to the resources.
```
deploy:	build_files
	$(info deploy $(env) environment)
	@for file in $(shell find $(BUILDS_DIR)/$(env) -type f -name "*.yaml" | sort); do \
		kubectl apply -f $$file ; \
	done
```

create_users.sh posts username & password data:
```
for i in {1..5}; 
do 
   username=$(curl --silent 'https://frightanic.com/goodies_content/docker-names.php');
   password=$(tr -cd '[:alnum:]' < /dev/urandom | fold -w32 | head -n1;echo;);
   curl -X POST -H 'Content-Type: application/json' -i http://"${fqdn}"/users/create --data '{"username":"'"${username}"'","password":"'"${password}"'"}';
   echo;
done
```


# build and deploy spring boot app to [k3s](https://k3s.io/)

### Prerequisites

* Installed Docker to build and push an image to registry.
* [Installed and configured kubernetes](https://github.com/bobanbojkovski/k3s) - [k3s](https://k3s.io/) cluster. 
* Spring Boot application


### Demo uses Makefile to build and deploy spring boot app and mysql db to kubernetes

Use **make env=environment build_target** syntax to trigger Makefile target, for example:<br/>
make env=dev deploy or
make env=dev docker_build

Environment variables are defined in dev.env, test.env files and mapped in template configurations placed under **templates** directory.

Also update <node_name> in persistent volume file:

```
required:
      nodeSelectorTerms:
      - matchExpressions:
        - key: kubernetes.io/hostname
          operator: In
          values:
          - <node_name>
```

**build_files** target in Makefile prepares environment specific deployments in **deployments** directory. The dir should be manually created before running the task.

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


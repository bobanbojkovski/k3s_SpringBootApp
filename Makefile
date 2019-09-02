
ifndef env
$(error env is not set)
endif

ifneq (,$(filter $(env), dev test))
	include ./$(env).env
endif


TEMPLATES_DIR	?=	./templates
BUILDS_DIR		?=	./deployments

MAKE_ENV += NS H
SHELL_EXPORT := $(foreach v,$(MAKE_ENV),$(v)='$($(v))' )


docker_build:
	$(info docker build environment images)
	@docker build . -t localhost:5000/demo:0.1 --no-cache --pull
	
docker_push:
	$(info docker push environment images)
	@docker push localhost:5000/demo:0.1

build_files:
	$(info prepare deploy files)
	@mkdir -p $(BUILDS_DIR)/$(env)
	@for file in $(shell find $(TEMPLATES_DIR) -type f -name '*.yaml' | sed 's#.*/##' | sort ) ; do \
		$(SHELL_EXPORT) envsubst '$$NS,$$H' < $(TEMPLATES_DIR)/$$file > $(BUILDS_DIR)/$(env)/$$file ;\
	done

deploy:	build_files
	$(info deploy $(env) environment)
#	@make --silent build_files
	@for file in $(shell find $(BUILDS_DIR)/$(env) -type f -name "*.yaml" | sort); do \
		kubectl apply -f $$file ; \
	done

delete:
	$(info delete $(env) environment)
	@for file in $(shell find $(BUILDS_DIR)/$(env) -type f -name "*_*.yaml" | sort -r); do \
		kubectl delete -f $$file ; \
	done
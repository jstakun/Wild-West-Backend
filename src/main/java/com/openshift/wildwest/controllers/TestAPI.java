package com.openshift.wildwest.controllers;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.KubernetesClient;

import javax.inject.Inject;

@RestController
public class TestAPI {

    @Inject
	KubernetesClient client;

	// oc policy add-role-to-user view system:serviceaccount:wildwest:default where wildwest
	// is the project name

	// To enable destructive mode, a different permission needs to be added
	// oc policy add-role-to-user edit system:serviceaccount:wildwest:default

	@RequestMapping("/kube")
	public Hashtable getPlatformObjects() {
		Hashtable hashtable = new Hashtable<>();
		
		for (Pod currPod : client.pods().list().getItems()) {
			hashtable.put(currPod.getMetadata().getUid(), currPod.getMetadata().getName());
		}

		return hashtable;

	}
}
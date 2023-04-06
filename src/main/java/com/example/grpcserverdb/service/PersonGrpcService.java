package com.example.grpcserverdb.service;

import com.example.grpc.PersonServiceGrpc;
import com.example.grpc.PersonServiceOuterClass;
import com.example.grpcserverdb.dao.PersonDao;
import com.example.grpcserverdb.model.Person;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersonGrpcService extends PersonServiceGrpc.PersonServiceImplBase {

    private final PersonDao personDao = new PersonDao();

    private static final Logger LOGGER = Logger.getLogger(PersonGrpcService.class.getName());

    @Override
    public void getPersons(PersonServiceOuterClass.PersonsReq request, StreamObserver<PersonServiceOuterClass.PersonAllRes> responseObserver) {

        Integer orgId = request.getOrgId();

        try{
            List<Person> persons = (List<Person>) personDao.findAll(orgId);
            List<PersonServiceOuterClass.Person> personList = new ArrayList<>();
            persons.forEach(p -> {
                PersonServiceOuterClass.Person person = PersonServiceOuterClass.Person.newBuilder()
                        .setId(p.getId())
                        .setFirstname(p.getFirstname())
                        .setLastname(p.getLastname())
                        .setAge(p.getAge())
                        .setOrgId(p.getOrgid()).build();

                personList.add(person);
            });

            PersonServiceOuterClass.PersonAllRes res = PersonServiceOuterClass.PersonAllRes.newBuilder().addAllPersons(personList).build();

            responseObserver.onNext(res);
            responseObserver.onCompleted();

        }catch (NoSuchElementException e){
            LOGGER.log(Level.SEVERE, "No result found persons orgid: " + orgId);
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }


    }

    @Override
    public void getPerson(PersonServiceOuterClass.PersonReq request, StreamObserver<PersonServiceOuterClass.Person> responseObserver) {

        Long id = request.getId();

        try{
            Person person = personDao.findById(id);
            PersonServiceOuterClass.Person per = PersonServiceOuterClass.Person.newBuilder()
                    .setFirstname(person.getFirstname())
                    .setLastname(person.getLastname())
                    .setId(person.getId())
                    .setAge(person.getAge())
                    .setOrgId(person.getOrgid()).build();
            responseObserver.onNext(per);
            responseObserver.onCompleted();
        }catch (NoSuchElementException e){
            LOGGER.log(Level.SEVERE, "No result found person id: " + id);
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }



    }

}

package com.ghb.coltpath.rest;

import com.ghb.coltpath.dto.reader.PathGet;
import com.ghb.coltpath.dto.writer.PathPost;
import com.ghb.coltpath.dto.writer.PathUsersPost;
import com.ghb.coltpath.dto.writer.RequestOutcomeMessage;
import com.ghb.coltpath.model.Path;
import com.ghb.coltpath.service.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Ghebo on 1/20/2016.
 */
@RestController
public class PathResource {

    @Autowired
    PathService pathService;


    @RequestMapping(value = "/paths/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    PathGet showPath(@PathVariable long id) {
        Path path = pathService.getPath(id);
        PathGet response = new PathGet(path.getId(), path.getName(), path.getCreatedBy(), path.getCreationDate());

        response.add(linkTo(methodOn(PathResource.class).showPath(id)).withSelfRel());
        return response;
    }

    @RequestMapping(value = "/paths/{id}/users", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<RequestOutcomeMessage> addUser(@PathVariable Long id, @Validated @RequestBody PathUsersPost
            pathUsersPost) throws InterruptedException {

        boolean success = pathService.addUserToPath(id, pathUsersPost.getUserIds());
        if (!success) {
            return new ResponseEntity<RequestOutcomeMessage>(new RequestOutcomeMessage("Path does not exist"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<RequestOutcomeMessage>(new RequestOutcomeMessage("User added successfully to path"), HttpStatus.OK);
    }

    @RequestMapping(value = "/paths", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PathGet> showAll() {
        List<Path> paths = pathService.getPaths();
        List<PathGet> response = new ArrayList<>();
        for (Path path : paths) {
            PathGet pathGet = new PathGet(path.getId(), path.getName(), path.getCreatedBy(), path.getCreationDate());
            pathGet.add(linkTo(methodOn(PathResource.class).showPath(path.getId())).withSelfRel());
            response.add(pathGet);
        }
        return response;
    }

    @RequestMapping(value = "/paths", method = RequestMethod.POST)
    public
    @ResponseBody
    PathGet createPath(@Validated @RequestBody PathPost pathPost) {
        Path path = pathService.createPath(pathPost);
        PathGet response = new PathGet(path.getId(), path.getName(), path.getCreatedBy(), path.getCreationDate());

        response.add(linkTo(methodOn(PathResource.class).showPath(path.getId())).withSelfRel());
        return response;
    }
}

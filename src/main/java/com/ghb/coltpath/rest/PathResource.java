package com.ghb.coltpath.rest;

import com.ghb.coltpath.dto.reader.PathGet;
import com.ghb.coltpath.model.Path;
import com.ghb.coltpath.service.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}

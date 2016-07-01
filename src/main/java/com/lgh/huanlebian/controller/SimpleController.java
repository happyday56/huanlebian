package com.lgh.huanlebian.controller;

import org.eclipse.persistence.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by lgh on 2016/6/27.
 */

/**
 * todo search
 * @param <T>
 * @param <ID>
 * @param <S>
 */
public abstract class SimpleController<T extends SimpleController.ICanHandleEntity<ID>, ID extends Serializable, S> {

    public interface ICanHandleEntity<ID> {

        ID geiWoID();

    }

    @Autowired
    private JpaSpecificationExecutor<T> jpaSpecificationExecutor;
    @Autowired
    private JpaRepository<T, ID> jpaRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    @Transactional(readOnly = true)
    public String list(Pageable pageable, S search, Model model) {
        Page<T> result = searchData(pageable, search);

        model.addAttribute("list", result);
        return indexViewName();
    }

    private Page<T> searchData(Pageable pageable, S search) {
        Page<T> result;
        if (search == null) {
            result = jpaRepository.findAll(pageable);
        } else {
            Specification<T> specification = searchSpecification(search);
            if (specification == null) {
                result = jpaRepository.findAll(pageable);
            } else
                result = jpaSpecificationExecutor.findAll(specification, pageable);
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Transactional(readOnly = true)
    public Object listJson(Pageable pageable, S search) {
        return searchData(pageable, search);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<ID> add(@RequestHeader(value = "X-Requested-With", required = false) String ajax, T data) throws URISyntaxException {
        //
        data = handleForAdd(data);

        data = jpaRepository.save(data);

        if (StringUtils.isEmpty(ajax)) {
            String uri = this.getClass().getAnnotation(RequestMapping.class).value()[0];
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(uri));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(data.geiWoID(), HttpStatus.OK);
        }
    }

    /**
     * @param data 用户提交的数据
     * @return 系统需要保存的数据
     */
    protected T handleForAdd(T data) throws ValidationException {
        return data;
    }

    ;

    /**
     * @return 索引页视图名
     */
    protected abstract String indexViewName();

    protected Specification<T> searchSpecification(S search) {
        return null;
    }


}
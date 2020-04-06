package home.konstantin.supplier.api;

import home.konstantin.supplier.dto.PersonApiDto;
import home.konstantin.supplier.service.SchedulerSender;
import home.konstantin.supplier.service.SupplierSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("api/persons")
@RequiredArgsConstructor
public class PersonApi {

    private final SchedulerSender schedulerSender;
    private final SupplierSender supplierSender;

    @RequestMapping("/request")
    @PostMapping
    public void request(@Valid @RequestBody PersonApiDto assetDto) {
        supplierSender.setPerson(assetDto);
    }

    @PutMapping("/enable-scheduler")
    public void activate(@PathVariable boolean enable) {
        schedulerSender.setEnabled(enable);
    }


}

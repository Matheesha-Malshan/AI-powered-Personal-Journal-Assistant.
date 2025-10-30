package com.intelligentDiary.strategy.processing.processingImpl;

import com.intelligentDiary.model.InputTypeFormat;
import com.intelligentDiary.strategy.processing.ProcessStrategy;
import com.intelligentDiary.strategy.processing.ProcessingStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class StrategySelector implements ProcessStrategy {

    Map<InputTypeFormat,ProcessingStrategy> processStrategyMap=new HashMap<>();

    public StrategySelector(List<ProcessingStrategy> strategyList){

        for (InputTypeFormat format:InputTypeFormat.values()){
            for (ProcessingStrategy strategy:strategyList){
                if (strategy.checkStrategy(format)){
                    processStrategyMap.put(format,strategy);
                }
            }
        }
    }
    @Override
    public ProcessingStrategy selectStrategy(InputTypeFormat format){
        return processStrategyMap.get(format);
    }

}

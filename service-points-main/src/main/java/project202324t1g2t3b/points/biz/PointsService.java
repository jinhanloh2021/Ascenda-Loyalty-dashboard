package project202324t1g2t3b.points.biz;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project202324t1g2t3b.points.api.models.Points;
import project202324t1g2t3b.points.biz.models.PointsBiz;
import project202324t1g2t3b.points.data.PointsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PointsService {

    private final ModelMapper modelMapper;

    private final PointsRepository pointsRepository;

    @Autowired
    public PointsService(PointsRepository pointsRepository, ModelMapper modelMapper) {
        this.pointsRepository = pointsRepository;
        this.modelMapper = modelMapper;
    }

    public Points createPoints(Points newPoints) {

        PointsBiz newPointsBiz = modelMapper.map(newPoints, PointsBiz.class);
        pointsRepository.createPoints(newPointsBiz);
        log.info(newPointsBiz.toString() + " passed to PointsRepository");
        return newPoints;
    }

    public List<Points> getAllPoints() {
        return pointsRepository.getAllPoints()
                .stream()
                .map(pointsBiz -> modelMapper.map(pointsBiz, Points.class))
                .collect(Collectors.toList());
    }

    public Points getPoints(String pointsId) {
        return modelMapper.map(pointsRepository.getPoints(pointsId), Points.class);
    }

    public Points updatePoints(String pointsId, Points newPoints) {
        PointsBiz newPointsBiz = modelMapper.map(newPoints, PointsBiz.class);
        return modelMapper.map(pointsRepository.updatePoints(pointsId, newPointsBiz), Points.class);
    }

    public Points deletePoints(String pointsId) {
        return modelMapper.map(pointsRepository.deletePoints(pointsId), Points.class);
    }

    public List<Points> getAllUsersPoints(String userId) {
        return pointsRepository.getAllUsersPoints(userId)
                .stream()
                .map(pointsBiz -> modelMapper.map(pointsBiz, Points.class))
                .collect(Collectors.toList());
    }

    public List<Points> getUsersPointsByAppName(String userId, String appName) {
        List<Points> pointsUser = pointsRepository.getAllUsersPoints(userId)
                .stream()
                .map(pointsBiz -> modelMapper.map(pointsBiz, Points.class))
                .collect(Collectors.toList());

        log.info("Got all user points");
        List<Points> pointsUserApp = new ArrayList<>();
        for (Points p : pointsUser) {
            if (p.getAppName().equals(appName)) {
                pointsUserApp.add(p);
            }
        }
        return pointsUserApp;

    }


}

package br.com.projeto.app.controller;

import br.com.projeto.core.base.Controller;
import br.com.projeto.core.base.HttpMethod;
import br.com.projeto.core.base.ServletRouter.Handler;
import br.com.projeto.core.dao.HotelDAO;
import br.com.projeto.core.entity.Hotel;
import br.com.projeto.utils.Utils;

public class HotelController extends Controller {
    HotelDAO hotelDAO;

    public HotelController(HotelDAO hotelDAO) {
        this.registerRoute(HttpMethod.GET, "/hotel", this.getHotelData);

        this.hotelDAO = hotelDAO;
    }

    private Handler getHotelData = (req, res) -> {
        Hotel hotel = this.hotelDAO.obterHotel();

        Utils.sendJsonResponse(Utils.convertObjectToJson(hotel), res);
    };

}

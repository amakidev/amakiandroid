package br.com.victorcatao.amaki.data.repositories

import br.com.victorcatao.amaki.data.remote.models.Picture
import io.reactivex.Single


object PicturesRepository {

    fun getPictures(): Single<List<Picture>> {

        var pictures = listOf<Picture>(
                Picture("", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcSx9enYq4jWYyI9UuM0yZfgMXfb1weJAtp3cr4pWv2AVWJWkNtw"),
                Picture("", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSNu2JmOoOAnyn1HGfRUp6V95mcjVNglyX5v7IGRqT7o1tDY0Ibg"),
                Picture("", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmFj0I21cO0tMglbMjsmJM3Uc7yXUpcjYDkG-fq9LZJx9_mgtw"),
                Picture("", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8RCbM4WDe6lLlUTayfGL_BjxMEJKK_kG7e25qbn-lk0Rt0-o9"),
                Picture("", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2G3SuBAAftKEPIw4pGdVV_QO3PqP5gTXQ53DT81rjLoxPzbAC"),
                Picture("", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1jZjc66dxB6Eusu840X21m071A-7k7KBSy2mYjiRkpzlsyircKQ"),
                Picture("", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrty2CLHvO4WAxnFCN-VkjKdAuBVnG4pzFK2zw8tkVrHbDXibktQ"),
                Picture("", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQP1xx-0UFzEdVMyK697_W9usZdXBB0F2-rZLtijHcTKtlThB5"),
                Picture("", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-ejAdHUdOpeBSPtL46cWndYKW2Moa2lrtk2zFUMCZlWI1sz4Y"),
                Picture("", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgClabn_wCqge02jhHz_w607QSAMWfqQpF9_ucZdWf8ozrPP3O"),
                Picture("", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQz5PUGu5pnbjW6cWmtH3JybnNUWvY21dKBLcJfYbbZls6q4euy"),
                Picture("", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRG3aDc7KWa_utQPW3t2SN3fMQ64a29nSUeVxBSa9swmVy8tmda")
        )

        pictures = pictures.shuffled()

        return Single.just(pictures)
    }

//todo Implement the methods below according to the project

//    fun removePicture(pictureId: String){
//
//    }

//    fun addPicture(file: File?): Single<Any> {
//        val picturePart = file?.let {
//            MultipartBody.Part.createFormData("picture", file.name, RequestBody.create(MediaType.parse("image/*"), file))
//        }
//
//        return PicturesRemoteDataSource.addPicture(picturePart)
//    }

}
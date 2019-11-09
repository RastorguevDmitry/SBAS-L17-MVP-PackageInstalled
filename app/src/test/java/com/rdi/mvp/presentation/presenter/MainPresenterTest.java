package com.rdi.mvp.presentation.presenter;


import com.rdi.mvp.data.model.InstalledPackageModel;
import com.rdi.mvp.data.repository.PackageInstalledRepository;
import com.rdi.mvp.presentation.view.IPackageInstalledView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    private IPackageInstalledView mPackageInstalledView;

    @Mock
    private PackageInstalledRepository mPackageInstalledRepository;

    private MainPresenter mMainPresenter;

    /**
     * Данный метод будет вызван перед каждым тестовым методом.
     */
    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter(mPackageInstalledView, mPackageInstalledRepository);
    }

    /**
     * Тестирование синхронного получения данных в презентере.
     */
    @Test
    public void testLoadDataSync() {
        //Создание мока для получения данных из репозитория (необходимо создавать мок до вызова тестируемого метода)
        when(mPackageInstalledRepository.getData(anyBoolean())).thenReturn(createTestData());

        //Вызов тестируемого метода
        mMainPresenter.loadDataSync();

        //Проверка, что презентер действительно вызывает методы представления
        verify(mPackageInstalledView).showProgress();
        verify(mPackageInstalledView).showData(createTestData());
        verify(mPackageInstalledView).hideProgress();
    }

    /**
     * Тестирование синхронного метода получения данных в презентере.
     * <p> В данном тесте дополнительно проверяется порядок вызова методов. Если поменять очередность или добавить какой-либо вызов
     * метода {@link IPackageInstalledView} в {@link MainPresenter}, данный тест не пройдет.
     */
    @Test
    public void testLoadDataSync_withOrder() {
        //Создание мока для получения данных из репозитория (необходимо создавать мок до вызова тестируемого метода)
        when(mPackageInstalledRepository.getData(anyBoolean())).thenReturn(createTestData());

        //Вызов тестируемого метода
        mMainPresenter.loadDataSync();

        InOrder inOrder = Mockito.inOrder(mPackageInstalledView);

        //Проверка, что презентер действительно вызывает методы представления, причем в порядке вызова этих методов. Можно сравнить с предыдущим тестом.
        inOrder.verify(mPackageInstalledView).showProgress();
        inOrder.verify(mPackageInstalledView).hideProgress();
        inOrder.verify(mPackageInstalledView).showData(createTestData());

        //Проверка, что никакой метод не будет вызван у mPackageInstalledView.
        inOrder.verifyNoMoreInteractions();
    }

    /**
     * Тестирование асинхронного метода получения данных в презентере.
     */
    @Test
    public void testLoadDataAsync() {
        // Нам нужно выдернуть аргмуент, переданный в mPackageInstalledRepository в качетсве слушателя и немедленно вернуть
        //какой-то результат. Ведь нам неважно, каким образом отработает mPackageInstalledRepository#loadDataAsync(), важно, что этот метод должен вернуть
        //в колбеке.
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                //получаем слушателя из метода loadDataAsync().
                PackageInstalledRepository.OnLoadingFinishListener onLoadingFinishListener =
                        (PackageInstalledRepository.OnLoadingFinishListener) invocation.getArguments()[1];

                //кидаем в него ответ
                onLoadingFinishListener.onFinish(createTestData());

                return null;
            }
        }).when(mPackageInstalledRepository).loadDataAsync(anyBoolean(), Mockito.any(PackageInstalledRepository.OnLoadingFinishListener.class));

        mMainPresenter.loadDataAsync();

        //Далее просто проверяем, что все будет вызвано в нужном порядке.
        InOrder inOrder = Mockito.inOrder(mPackageInstalledView);
        inOrder.verify(mPackageInstalledView).showProgress();
        inOrder.verify(mPackageInstalledView).hideProgress();
        inOrder.verify(mPackageInstalledView).showData(createTestData());

        inOrder.verifyNoMoreInteractions();
    }

    /**
     * Тестирование {@link MainPresenter#detachView()}.
     *
     * <p> после детача, все методы не будут ничего прокидывать в {@link IPackageInstalledView}.
     */
    @Test
    public void testDetachView() {
        mMainPresenter.detachView();

        mMainPresenter.loadDataAsync();
        mMainPresenter.loadDataSync();

        verifyNoMoreInteractions(mPackageInstalledView);
    }

    private List<InstalledPackageModel> createTestData() {
        List<InstalledPackageModel> testData = new ArrayList<>();

        testData.add(new InstalledPackageModel("Sberbank",
                "ru.sberbankmobile", null));
        testData.add(new InstalledPackageModel("Test", "TestPackage",
                null));

        return testData;
    }
}